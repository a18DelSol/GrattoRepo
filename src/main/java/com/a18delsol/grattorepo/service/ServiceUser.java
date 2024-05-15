package com.a18delsol.grattorepo.service;

import com.a18delsol.grattorepo.model.ModelItem;
import com.a18delsol.grattorepo.model.ModelUserCart;
import com.a18delsol.grattorepo.model.ModelUser;
import com.a18delsol.grattorepo.repository.RepositoryItem;
import com.a18delsol.grattorepo.repository.RepositoryUserCart;
import com.a18delsol.grattorepo.repository.RepositoryUser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Optional;

public class ServiceUser {
    /* GENERAL */

    public static ModelUser userCreate(ModelUser user, RepositoryUser repository) {
        repository.save(user);

        return user;
    }

    public static ModelUser userPatch(JsonPatch patch, ModelUser user) throws JsonPatchException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.treeToValue(patch.apply(objectMapper.convertValue(user, JsonNode.class)), ModelUser.class);
    }

    /* EXCLUSIVE */

    public static ResponseEntity<String> userSignUp(ModelUser user, RepositoryUser repository) {
        ModelUser modelUserFind = repository.findByUserMail(user.getUserMail());

        if (modelUserFind != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Mail already in use.");
        }

        repository.save(user);

        return new ResponseEntity<>("Sign up OK.", HttpStatus.OK);
    }

    public static ResponseEntity<ModelUser> userSignIn(ModelUser user, RepositoryUser repository) {
        ModelUser modelUserFind = repository.findByUserMail(user.getUserMail());

        if (modelUserFind == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found.");
        } else if (!modelUserFind.getUserPass().equals(user.getUserPass())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pass is incorrect.");
        }

        return new ResponseEntity<>(modelUserFind, HttpStatus.OK);
    }

    public static ResponseEntity<ModelUser> userCartAttach(Integer userID, RepositoryUser repositoryUser, RepositoryItem repositoryItem,
        RepositoryUserCart repositoryUserCart, ModelUserCart cart) {
        Optional<ModelUser> modelUserFind = repositoryUser.findById(userID);
        Optional<ModelItem> modelItemFind;

        if (cart.getCartItem().getItemID() != null) {
            modelItemFind = repositoryItem.findById(cart.getCartItem().getItemID());
        } else {
            modelItemFind = repositoryItem.findByItemCode(cart.getCartItem().getItemCode());
        }

        if (modelUserFind.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
        } else if (modelItemFind.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found.");
        }

        ModelUser user = modelUserFind.get();
        ModelItem item = modelItemFind.get();

        if (cart.getCartCount() < 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cart item count cannot be less than 1.");
        } else if (cart.getCartCount() > item.getItemCount()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cart item count cannot be more than the item count.");
        } else if (item.getItemRestrict() && LocalDate.now().getYear() - user.getUserBirth().getYear() < 18) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User cannot add item to cart due to being underage.");
        }

        cart.setCartItem(item);
        repositoryUserCart.save(cart);

        user.getUserCart().add(cart);
        repositoryUser.save(user);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    public static ResponseEntity<ModelUser> userCartDetach(Integer userID, RepositoryUser repositoryUser, RepositoryUserCart repositoryUserCart, Integer cartID) {
        Optional<ModelUser> modelUserFind = repositoryUser.findById(userID);
        Optional<ModelUserCart> modelCartFind = repositoryUserCart.findById(cartID);

        if (modelUserFind.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found.");
        } else if (modelCartFind.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cart not found.");
        }

        ModelUser user = modelUserFind.get();
        ModelUserCart cart = modelCartFind.get();

        user.getUserCart().remove(cart);
        repositoryUser.save(user);

        repositoryUserCart.delete(cart);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
