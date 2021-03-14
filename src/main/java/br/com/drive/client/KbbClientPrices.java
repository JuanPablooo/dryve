package br.com.drive.client;

import br.com.drive.model.client.KbbPrice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;
import java.util.Optional;

interface KbbClientPrices {
    @GetMapping("/prices/{id}")
    ResponseEntity<Optional<KbbPrice>> getOne(@PathVariable Long id);

    @GetMapping("/prices/")
    ResponseEntity<List<KbbPrice>> getAll(@PathVariable Long id);

}
