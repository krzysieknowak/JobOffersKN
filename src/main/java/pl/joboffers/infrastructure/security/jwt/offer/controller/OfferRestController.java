package pl.joboffers.infrastructure.security.jwt.offer.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.joboffers.domain.offer.OfferFacade;
import pl.joboffers.domain.offer.offerdto.SaveOfferRequestDto;
import pl.joboffers.domain.offer.offerdto.SaveOfferResultDto;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor

public class OfferRestController {
    private final OfferFacade offerFacade;
    @GetMapping("/offers")
    public ResponseEntity <List<SaveOfferResultDto>> findAllOffers(){
        List<SaveOfferResultDto> offers = offerFacade.findAllOffers();
        return ResponseEntity.ok(offers);
    }

    @GetMapping("/offers/{id}")
    public ResponseEntity <SaveOfferResultDto> getOfferById(@PathVariable String id){
        SaveOfferResultDto resultOffer = offerFacade.findOfferById(id);
        return ResponseEntity.ok(resultOffer);
    }
    @PostMapping("/offers")
    public ResponseEntity <SaveOfferResultDto> addOffer(@RequestBody @Valid SaveOfferRequestDto saveOfferRequestDto){
        SaveOfferResultDto savedOffer = offerFacade.saveOfferToDatabase(saveOfferRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedOffer);
    }
}
