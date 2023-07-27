package pl.joboffers.infrastructure.offer.controller;

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
@RequestMapping("/offers")
public class OfferRestController {

    private final OfferFacade offerFacade;

    @GetMapping
    public ResponseEntity <List<SaveOfferResultDto>> findAllOffers(){
        List<SaveOfferResultDto> offers = offerFacade.findAllOffers();
        return ResponseEntity.ok(offers);
    }

    @GetMapping("/{id}")
    public ResponseEntity <SaveOfferResultDto> getOfferById(@PathVariable String id){
        SaveOfferResultDto resultOffer = offerFacade.findOfferById(id);
        return ResponseEntity.ok(resultOffer);
    }
    @PostMapping
    public ResponseEntity <SaveOfferResultDto> addOffer(@RequestBody @Valid SaveOfferRequestDto saveOfferRequestDto){
        SaveOfferResultDto savedOffer = offerFacade.saveOfferToDatabase(saveOfferRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedOffer);
    }
}
