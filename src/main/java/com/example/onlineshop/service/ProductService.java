package com.example.onlineshop.service;

import com.example.onlineshop.criteria.*;
import com.example.onlineshop.domain.entity.*;
import com.example.onlineshop.dto.*;
import com.example.onlineshop.mapper.*;
import com.example.onlineshop.repository.*;
import com.example.onlineshop.response.*;
import com.example.onlineshop.service.helper.*;
import org.hibernate.exception.*;
import org.springframework.dao.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.util.*;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final GlobalMapper globalMapper;

    public ProductService(ProductRepository productRepository,
                          ProductCategoryRepository productCategoryRepository,
                          GlobalMapper globalMapper) {
        this.productRepository = productRepository;
        this.productCategoryRepository = productCategoryRepository;
        this.globalMapper = globalMapper;
    }

    @Transactional
    public ProductResponse createProduct(ProductCreateDto productCreateDto) {
        ProductResponse productResponse = new ProductResponse();

        doCreate(productResponse, productCreateDto);

        return productResponse;
    }

    @Transactional(readOnly = true)
    public GenericPageableResponse<ProductDto> getProducts(ProductSearchCriteria productSearchCriteria) {
        PageRequest pageRequest = Creator.createPageRequest(productSearchCriteria);

        Page<Product> products = productRepository.findAll(pageRequest);

        return new GenericPageableResponse<>(
                globalMapper.mapList(products.getContent(), ProductDto.class),
                products.getTotalPages(),
                productSearchCriteria.getPageNumber()
        );
    }

    @Transactional(readOnly = true)
    public ProductResponse getProduct(Long productId) {
        ProductResponse productResponse = new ProductResponse();
        Optional<Product> product = productRepository.findById(productId);

        if (product.isEmpty()) {
            String message = String.format("Product with id %d not found.", productId);
            addFailureMessageToResponse(productResponse, message);
        } else {
            productResponse.setProductDto(globalMapper.map(product.get(), ProductDto.class));
        }

        return productResponse;
    }

    //This for example. We can change first 6 lines of previous method with this method.
    private ProductResponse getProductById(Long id) {
        ProductResponse productResponse = new ProductResponse();
        productRepository.findById(id).ifPresentOrElse(product ->
                        productResponse.setProductDto(globalMapper.map(product, ProductDto.class)),
                () -> {
                    String message = String.format("Product with id %d not found.", id);
                    addFailureMessageToResponse(productResponse, message);
                });

        return productResponse;
    }

    @Transactional
    public ProductResponse updateProduct(Long productId, ProductUpdateDto productUpdateDto) {
        ProductResponse productResponse = new ProductResponse();
        Optional<Product> product = productRepository.findById(productId);

        if (product.isEmpty()) {
            String message = String.format("Product with id %d not found.", productId);
            addFailureMessageToResponse(productResponse, message);

            return productResponse;
        }

        Product productToUpdate = product.get();
        setNotNullFieldsFromPrdUpdDtoToProduct(productToUpdate, productUpdateDto);

        Product updatedProduct = productRepository.save(productToUpdate);

        productResponse.setProductDto(globalMapper.map(updatedProduct, ProductDto.class));

        return productResponse;
    }

    @Transactional
    public ProductResponse deleteProduct(Long productId) {
        ProductResponse productResponse = new ProductResponse();
        Optional<Product> product = productRepository.findById(productId);

        if (product.isEmpty()) {
            String message = String.format("Product with id %d not found.", productId);
            addFailureMessageToResponse(productResponse, message);

            return productResponse;
        }

        productRepository.deleteProductById(productId);

        productResponse.setProductDto(globalMapper.map(product.get(), ProductDto.class));

        return productResponse;
    }

    private void doCreate(ProductResponse productResponse, ProductCreateDto productCreateDto) {
        Optional<ProductCategory> productCategory =
                productCategoryRepository.findById(productCreateDto.getProductCategoryId());
        if (productCategory.isEmpty()) {
            String message = String.format(
                    "Product category with %d name does not exist.", productCreateDto.getProductCategoryId());
            addFailureMessageToResponse(productResponse, message);
            throw new DataIntegrityViolationException(message);
        }
        Product productToSave =
                Product.builder()
                        .name(productCreateDto.getName())
                        .price(productCreateDto.getPrice())
                        .count(productCreateDto.getCount())
                        .build();
        productToSave.setProductCategory(productCategory.get());

        try {
            Product savedProduct = productRepository.save(productToSave);
            productResponse.setProductDto(globalMapper.map(savedProduct, ProductDto.class));
        } catch (DataIntegrityViolationException | ConstraintViolationException ex) {
            addFailureMessageToResponse(productResponse, ex.getMessage());
        }
    }

    private void setNotNullFieldsFromPrdUpdDtoToProduct(Product product, ProductUpdateDto productUpdateDto) {
        String name = productUpdateDto.getName();
        Long price = productUpdateDto.getPrice();
        Long count = productUpdateDto.getCount();
        if (name != null) {
            product.setName(name);
        }
        if (price != null) {
            product.setPrice(price);
        }
        if (count != null) {
            product.setCount(count);
        }
    }

    private void addFailureMessageToResponse(CustomResponse customResponse, String message) {
        List<String> failureMessages = new ArrayList<>();
        failureMessages.add(message);
        customResponse.setFailureMessages(failureMessages);
    }
}
