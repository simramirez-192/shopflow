package com.shopflow.productservice.service;

import com.shopflow.productservice.dto.CreateProductDTO;
import com.shopflow.productservice.dto.ProductResponseDTO;
import com.shopflow.productservice.exception.ProductNotFoundException;
import com.shopflow.productservice.model.Product;
import com.shopflow.productservice.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private static final Logger log = LoggerFactory.getLogger(ProductService.class);
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) { this.productRepository = productRepository; }

    public ProductResponseDTO createProduct(CreateProductDTO dto) {
        log.info("Creando producto: {}", dto.getName());
        Product p = new Product();
        p.setName(dto.getName()); p.setDescription(dto.getDescription());
        p.setPrice(dto.getPrice()); p.setCategory(dto.getCategory()); p.setBrand(dto.getBrand());
        Product saved = productRepository.save(p);
        log.info("Producto creado ID: {}", saved.getId());
        return ProductResponseDTO.fromEntity(saved);
    }

    public ProductResponseDTO getProductById(Long id) {
        log.info("Buscando producto ID: {}", id);
        return ProductResponseDTO.fromEntity(productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Producto no encontrado: " + id)));
    }

    public List<ProductResponseDTO> getAllActiveProducts() {
        return productRepository.findByActiveTrue().stream()
                .map(ProductResponseDTO::fromEntity).collect(Collectors.toList());
    }

    public List<ProductResponseDTO> getByCategory(String category) {
        return productRepository.findByCategoryAndActiveTrue(category).stream()
                .map(ProductResponseDTO::fromEntity).collect(Collectors.toList());
    }

    public ProductResponseDTO updateProduct(Long id, CreateProductDTO dto) {
        Product p = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Producto no encontrado: " + id));
        p.setName(dto.getName()); p.setDescription(dto.getDescription());
        p.setPrice(dto.getPrice()); p.setCategory(dto.getCategory());
        p.setBrand(dto.getBrand()); p.setUpdatedAt(LocalDateTime.now());
        return ProductResponseDTO.fromEntity(productRepository.save(p));
    }

    public void deleteProduct(Long id) {
        Product p = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Producto no encontrado: " + id));
        p.setActive(false);
        productRepository.save(p);
        log.info("Producto ID {} desactivado", id);
    }
}
