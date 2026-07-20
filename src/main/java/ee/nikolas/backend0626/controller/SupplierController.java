package ee.nikolas.backend0626.controller;

import ee.nikolas.backend0626.dto.Supplier1Product;
import ee.nikolas.backend0626.dto.Supplier2Product;
import ee.nikolas.backend0626.services.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SupplierController {

    private final SupplierService supplierService;

    @GetMapping("supplier1")
    private List<Supplier1Product> getSupplier1Products() {
        return supplierService.getSupplier1Products();
    }

    @GetMapping("supplier2")
    private List<Supplier2Product> getSupplier2Products() {
        return supplierService.getSupplier2Products();
    }
}
