package com.jobapp.job.clients;

import com.jobapp.job.external.Review;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "REVIEW")
public interface ReviewClient {
    @GetMapping("/reviews")
    List<Review> getReviewsForCompany(@RequestParam("companyId") Long companyId);

}
