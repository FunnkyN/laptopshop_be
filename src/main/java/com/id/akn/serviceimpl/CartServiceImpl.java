package com.id.akn.serviceimpl;

import com.id.akn.exception.*;
import com.id.akn.model.*;
import com.id.akn.repository.LaptopColorRepository;
import com.id.akn.repository.LaptopRepository;
import com.id.akn.repository.UserRepository;
import com.id.akn.request.CartItemDTO;
import com.id.akn.service.CartItemService;
import com.id.akn.service.CartService;
import com.id.akn.service.ColorService;
import com.id.akn.service.LaptopService;
import org.springframework.stereotype.Service;

import com.id.akn.repository.CartRepository;

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService {

    private CartRepository cartRepository;
    private CartItemService cartItemService;
    private LaptopService laptopService;
    private ColorService colorService;
    private LaptopRepository laptopRepository;
    private LaptopColorRepository laptopColorRepository;
    private UserRepository userRepository;

    @Override
    public Cart createCart(User user) {
        Cart cart = new Cart();
        cart.setUser(user);
        return cartRepository.save(cart);
    }

    @Override
    public CartItemDTO addItemToCart(Long userId, CartItemDTO req) throws LaptopException, ColorException, CartItemException, UserException {
        Cart cart = cartRepository.findByUserId(userId);
        if (cart == null) {
            User user = userRepository.findById(userId).orElseThrow(() -> new UserException("User not found"));
            cart = createCart(user);
        }

        Laptop laptop = laptopRepository.findById(req.getLaptopId()).orElseThrow(() -> new LaptopException("Laptop not found"));
        int currentQuantity=0;
        byte colorId = req.getColorId();
        for(LaptopColor laptopColor: laptop.getLaptopColors()){
            if(laptopColor.getColor().getId() == colorId) {
                currentQuantity = laptopColor.getQuantity();
            }
        }
        CartItemDTO isPresent = cartItemService.isCartItemExist(cart, laptop, req.getColorId());
        int newQuantity=0;
        newQuantity = req.getQuantity();
        if(isPresent == null) {
            if(newQuantity >= currentQuantity) {
                newQuantity = currentQuantity;
            }
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setLaptop(laptop);
            cartItem.setColor(colorService.getColorById(req.getColorId()));
            cartItem.generateCompositeId();
            cartItem.setQuantity((short) newQuantity);
            CartItem managedCartItem = cartItemService.createCartItem(cartItem);

            cart.getCartItems().add(managedCartItem);
            cartRepository.save(cart);
            return convertToDTO(managedCartItem);
        }else{
            newQuantity += isPresent.getQuantity();
            isPresent.setQuantity((short) newQuantity);
            if(newQuantity >= currentQuantity) {
                newQuantity = currentQuantity;
            }
            for (CartItem item : cart.getCartItems()) {
                if (item.getId().equals(isPresent.getId())) {
                    item.setQuantity((short) newQuantity);
                }
            }
            cartItemService.updateCartItem(userId, isPresent.getId(), isPresent);
            cartRepository.save(cart);
        }
        return isPresent;
    }

    @Override
    public List<CartItemDTO> findUserCart(Long userId) {
        Cart cart = cartRepository.findByUserId(userId);
        
        if (cart == null) {
            User user = userRepository.findById(userId).orElse(null);
            if (user != null) {
                cart = createCart(user);
            } else {
                throw new RuntimeException("User not found with id: " + userId);
            }
        }

        return cart.getCartItems().stream()
                .map(this::convertToDTO).collect(Collectors.toList());
    }

    public CartItemDTO convertToDTO(CartItem cartItem) {

        CartItemDTO cartItemDTO = new CartItemDTO();

        cartItemDTO.setId(cartItem.getId());
        cartItemDTO.setLaptopId(cartItem.getLaptop().getId());
        cartItemDTO.setLaptopModel(cartItem.getLaptop().getModel());
        cartItemDTO.setLaptopPrice(cartItem.getLaptop().getPrice());
        cartItemDTO.setDiscountPercent(cartItem.getLaptop().getDiscountPercent());
        cartItemDTO.setColorId(cartItem.getColor().getId());
        cartItemDTO.setColorName(cartItem.getColor().getName());
        cartItemDTO.setQuantity(cartItem.getQuantity());

        LaptopColor laptopColor = laptopColorRepository.findByLaptopId(cartItem.getLaptop().getId());
        cartItemDTO.setStock(laptopColor.getQuantity());

        Set<String> imageUrls = cartItem.getLaptop().getImageUrls();
        if (imageUrls != null && !imageUrls.isEmpty()) {
            String firstImageUrl = imageUrls.iterator().next();
            cartItemDTO.setFirstImageUrl(firstImageUrl);
        } else {
            cartItemDTO.setFirstImageUrl(null);
        }

        return cartItemDTO;
    }
}