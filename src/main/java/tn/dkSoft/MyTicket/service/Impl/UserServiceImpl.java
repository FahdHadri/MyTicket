package tn.dkSoft.MyTicket.service.Impl;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.dkSoft.MyTicket.dto.CartDto;
import tn.dkSoft.MyTicket.dto.CartItemDto;
import tn.dkSoft.MyTicket.dto.UserDto;
import tn.dkSoft.MyTicket.exceptions.NotFoundException;
import tn.dkSoft.MyTicket.mappers.EventMapperImpl;
import tn.dkSoft.MyTicket.model.Cart;
import tn.dkSoft.MyTicket.model.CartItem;
import tn.dkSoft.MyTicket.model.User;
import tn.dkSoft.MyTicket.repository.CartRepository;
import tn.dkSoft.MyTicket.repository.UserRepository;
import tn.dkSoft.MyTicket.service.CartService;
import tn.dkSoft.MyTicket.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Getter
@Setter
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
@Slf4j
@Transactional
class UserServiceImpl implements UserService {
    @Autowired
    private final CartRepository cartRepository;
@Autowired
    private final UserRepository userRepository;

private final CartService cartService;

    @Override

    public UserDto registerUser(UserDto userDto) throws Exception {
        try {
            User user = EventMapperImpl.fromUserDto ( userDto );
            Cart cart = new Cart ();
            user.setCart ( cart );
            cart.setUser ( user );
            User registeredUser = userRepository.save ( user );
            Cart savedCart = cartRepository.save(cart);
           // Cart loadedCart = cartRepository.findById ( cart.getCartId () ).orElse ( null );
            UserDto registeredUserDto = EventMapperImpl.fromUser ( registeredUser );
            registeredUser.setCart ( savedCart );

            return registeredUserDto;
        } catch (DataIntegrityViolationException e) {
            throw new Exception ( "User already exists with emailId: " + userDto.getEmail () );
        }
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll ();
        return users.stream ()
                .map ( user -> {
                    UserDto userDto = EventMapperImpl.fromUser ( user );
                    if (user.getCart () != null) {
                        userDto.setCart ( EventMapperImpl.fromCart ( user.getCart () ) );
                    }
                    return userDto;
                } )
                .collect ( Collectors.toList () );
    }

    @Override
    public UserDto getUserById(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            UserDto userDto = EventMapperImpl.fromUser(user.get());
            if (user.get().getCart() != null) {
                userDto.setCart(EventMapperImpl.fromCart(user.get().getCart()));
                if (user.get().getCart().getCartItems() != null) {
                    List<CartItemDto> cartItemDtos = user.get().getCart().getCartItems().stream()
                            .map(cartItem -> EventMapperImpl.fromCartItem(cartItem))
                            .collect(Collectors.toList());
                    userDto.getCart().setCartItemDtos (cartItemDtos); // Corrected method name
                }
                return userDto;
            } else {
                // Handle user not found
                throw new NotFoundException("User not found with ID: " + userId);
            }
        } else {
            // Handle user not found
            throw new NotFoundException("User not found with ID: " + userId);
        }
    }

    @Transactional
    @Override
    public UserDto updateUser(Long userId, UserDto userDto) {
        // Retrieve the existing user from the repository
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        // Update user attributes based on the UserDto
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());

        // Save the updated user to the repository
        User updatedUser = userRepository.save(user);

        // Map the updated user to a UserDto
        UserDto updatedUserDto = EventMapperImpl.fromUser(updatedUser);

        // Map the user's cart and tickets information
        CartDto cartDto = EventMapperImpl.fromCart(updatedUser.getCart());


        updatedUserDto.setCart(cartDto);

        return updatedUserDto;
    }

@Override
    public void  deleteUser(Long userId) {
        log.info("Deleting User with ID: {}", userId);
    User user = userRepository.findById(userId)
            .orElseThrow(() -> new NotFoundException ("User not found"));
   List<CartItem> cartItems = user.getCart().getCartItems();
    Long cartId = user.getCart().getCartId();

    cartItems.forEach(item -> {

        Long TicketId = item.getTickets ().getId ();

        cartService.deleteTicketFromCart (cartId, TicketId);
    });
        userRepository.deleteById(userId);
    }
}


