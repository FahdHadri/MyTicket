package tn.dkSoft.MyTicket.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import tn.dkSoft.MyTicket.dto.CartDto;
import tn.dkSoft.MyTicket.exceptions.NotFoundException;
import tn.dkSoft.MyTicket.service.CartService;

import java.util.List;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
@Slf4j
public class CartController {
    @Autowired
private final CartService cartServiceInterface;

    private static final Logger logger = LoggerFactory.getLogger(CartController.class);

    @PostMapping("/carts/{cartId}/tickets/{ticketId}/quantity/{quantity}")
    public ResponseEntity<CartDto> addTicketToCart(@PathVariable Long cartId, @PathVariable Long ticketId, @PathVariable Integer quantity) throws Exception {
        logger.info("Adding ticket to cart. Cart ID: {}, Ticket ID: {}, Quantity: {}", cartId, ticketId, quantity);

        CartDto cartDto = cartServiceInterface.addTicketToCart(cartId, ticketId, quantity);

        logger.info("Ticket added to cart. Cart ID: {}, Ticket ID: {}, Quantity: {}", cartId, ticketId, quantity);

        return new ResponseEntity<>(cartDto, HttpStatus.CREATED);
    }

    @GetMapping("/carts")
    public ResponseEntity<List<CartDto>>getCarts() throws Exception {
        logger.info("Fetching all carts");

        List<CartDto> cartDTOs = cartServiceInterface.getAllCarts();

        logger.info("Fetched {} carts", cartDTOs.size());

        return new ResponseEntity<>(cartDTOs, HttpStatus.FOUND);
    }

    @GetMapping("/cart/{id}")
    public ResponseEntity<CartDto> getCart(@PathVariable(name = "id") Long id) {
        try {
            logger.info("Fetching cart with ID: {}", id);
            CartDto cart = cartServiceInterface.getCartById(id);
            if (cart != null) {
                logger.info("Cart found with ID: {}", id);
                return ResponseEntity.ok(cart);
            } else {
                logger.info("Cart not found with ID: {}", id);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.error("Error fetching cart with ID: " + id, e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error fetching cart with ID: " + id, e);
        }
    }

    @PutMapping("/{cartId}/update/{ticketId}")
    public ResponseEntity<String> updateTicketQuantityInCart(
            @PathVariable Long cartId,
            @PathVariable Long ticketId,
            @RequestParam Integer quantity
    ) {
        try {
            logger.info("Updating ticket quantity in cart. Cart ID: {}, Ticket ID: {}, Quantity: {}", cartId, ticketId, quantity);
            cartServiceInterface.updateTicketQuantityInCart(cartId, ticketId, quantity);
            logger.info("Ticket quantity updated in the cart. Cart ID: {}, Ticket ID: {}, Quantity: {}", cartId, ticketId, quantity);
            return new ResponseEntity<>("Ticket quantity updated in the cart.", HttpStatus.OK);
        } catch (NotFoundException e) {
            logger.error("Ticket not found when updating quantity in cart. Cart ID: {}, Ticket ID: {}, Quantity: {}", cartId, ticketId, quantity);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Error updating ticket quantity in cart. Cart ID: {}, Ticket ID: {}, Quantity: {}", cartId, ticketId, quantity, e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{cartId}/remove/{ticketId}")
    public ResponseEntity<String> deleteTicketFromCart(@PathVariable Long cartId, @PathVariable Long ticketId) {
        try {
            logger.info("Removing ticket from cart. Cart ID: {}, Ticket ID: {}", cartId, ticketId);
            String message = cartServiceInterface.deleteTicketFromCart(cartId, ticketId);
            logger.info("Ticket removed from cart. Cart ID: {}, Ticket ID: {}", cartId, ticketId);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (NotFoundException e) {
            logger.error("Ticket not found when removing from cart. Cart ID: {}, Ticket ID: {}", cartId, ticketId);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Error removing ticket from cart. Cart ID: {}, Ticket ID: {}", cartId, ticketId, e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}

