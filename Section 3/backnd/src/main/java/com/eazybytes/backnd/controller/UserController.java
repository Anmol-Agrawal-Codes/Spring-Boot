package com.eazybytes.backnd.controller;

import com.eazybytes.backnd.dto.UserDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/dummy/users/")
public class UserController {

    @GetMapping({"{userId}/posts/{postId}", "{userId}"}) // here we can define multiple api paths/endpoints
    public String searchUserPostWithMultiplePathVariables(@PathVariable Integer userId, @PathVariable(required = false) Integer postId) { // Path variable must match with the parameters.
        String response;
        if(postId == null){
            response = "Fetched user with id: " + userId;
        }
        else{
            response = "Fetched user with id: " + userId + " and post id: " + postId;
        }
        return response;
    }

    @GetMapping({"{customerId}/orders/{orderId}"}) // here we can define multiple api paths/endpoints
    public String searchUserOrderWithMultiplePathVariables(@PathVariable(name = "customerId") Integer userId, @PathVariable(required = false) Integer orderId) { // (name) parameter providing the hint.
        String response;
        if(orderId == null){
            response = "Fetched customer with id: " + userId;
        }
        else{
            response = "Fetched customer with id: " + userId + " and order id: " + orderId;
        }
        return response;
    }

    @GetMapping({"{userId}/addresses/{addressId}"}) // here we can define multiple api paths/endpoints
    public String searchUserAddressWithMultiplePathVariables(@PathVariable Map<String, String> pathVariableMap) { // instead of taking variables separately we can take them at once.
        String response;
        if(pathVariableMap.get("addressId") == null){
            response = "Fetched user with id: " + pathVariableMap.get("userId");
        }
        else{
            response = "Fetched user with id: " + pathVariableMap.get("userId") + " and address id: " + pathVariableMap.get("addressId");
        }
        return response;
    }

    @GetMapping("/search")
    public String searchUserWithQueryParams(@RequestParam(required = false, defaultValue = "Guest") String name, @RequestParam String gender) {
        return "Fetched user with name: " + name + " and gender: " + gender;
    }

    @GetMapping("/search/map")
    public String searchUserWithMapQueryParams(@RequestParam Map<String, String> requestParams) {
        return "Fetched user with name: " + requestParams.get("name") + " and gender: " + requestParams.get("gender");
    }

    @GetMapping("/headers")
    public String readRequestHeader(@RequestHeader("User-Agent") String userAgent, @RequestHeader("User-Location") String userLocation) {
        return "Fetched user with agent: " + userAgent + " and location: " + userLocation;
    }

    @GetMapping("/headers/map")
    public String readRequestHeaderWithMap(@RequestHeader Map<String, String> requestHeaders) {
        return "Fetched user with agent: " + requestHeaders.get("User-Agent") + " and location: " + requestHeaders.get("User-Location");
    }

    @GetMapping("/headers/http")
    public String readRequestHeaderWithHttp(@RequestHeader HttpHeaders requestHeaders) {
        return "Fetched user with agent: " + requestHeaders.get("User-Agent") + " and location: " + requestHeaders.get("User-Location");
    }

    @PostMapping("createUser")
    public String createUser(@RequestBody UserDto userDto) {
        return "Created user using data: " + userDto.toString();
    }

    @PostMapping("/request-entity")
    public String createUserWithRequestEntity(RequestEntity<UserDto> requestEntity) {
        HttpHeaders headers = requestEntity.getHeaders();
        UserDto userDto = requestEntity.getBody();
        String queryParams = requestEntity.getUrl().getQuery();
        String pathVariables = requestEntity.getUrl().getPath();
        return "Created user with the data: " + userDto.toString() + " and path: " + pathVariables + " and queryParams: " + queryParams;
    }
}
