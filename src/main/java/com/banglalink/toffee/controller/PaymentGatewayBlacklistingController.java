package com.banglalink.toffee.controller;

import com.banglalink.toffee.annotation.AuthHeader;
import com.banglalink.toffee.models.dto.BlacklistedCountriesDto;
import com.banglalink.toffee.models.request.BlacklistCoutriesRequest;
import com.banglalink.toffee.service.GatewayBlacklistedCountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;


import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static com.banglalink.toffee.utils.ResponseBuilder.getSuccessResponse;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("${routePrefix}/${apiVersion}/gateway")
@RequiredArgsConstructor
public class PaymentGatewayBlacklistingController {
    private final GatewayBlacklistedCountryService blacklistingCountryService;

    @GetMapping("/country/blacklist")
    public ResponseEntity<?> fetchBlacklistedCountries(@RequestParam(value = "gatewayId", required = false) UUID gatewayId,
                                                       @RequestParam(value = "countryCode", required = false) String countryCode,
                                                       @AuthHeader(value = "decodedPayload.header") String decodedPayload) {
        List<BlacklistedCountriesDto> blacklistedCountries = blacklistingCountryService.fetchBlacklistedCountries(gatewayId, countryCode);

        return ok(getSuccessResponse(blacklistedCountries, "success"));
    }

    @PutMapping("/country/blacklist/{Id}")
    public ResponseEntity<?> updateBlacklistedCountry(@PathVariable("Id") Long blacklistedCountryId,
                                                      @RequestBody BlacklistCoutriesRequest blacklistCoutriesRequest,
                                                      @AuthHeader(value = "decodedPayload.header") String decodedPayload) {
        blacklistingCountryService.updateBlacklistedCountry(blacklistCoutriesRequest, blacklistedCountryId);

        return ok(getSuccessResponse(new HashMap(){}, "Gateway blacklisting updated successfully"));
    }

    @DeleteMapping("/country/blacklist/{Id}")
    public ResponseEntity<?> deleteBlacklistedCountry(@PathVariable("Id") Long blacklistedCountryId,
                                                      @AuthHeader(value = "decodedPayload.header") String decodedPayload) {
        blacklistingCountryService.deleteBlacklistedCountry(blacklistedCountryId);

        return ok(getSuccessResponse(new HashMap(){}, "Gateway blacklisting deleted successfully"));
    }
}
