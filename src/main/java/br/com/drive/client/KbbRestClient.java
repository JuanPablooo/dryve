package br.com.drive.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "kbbClient", url = "${kbb.client.url}")
public interface KbbRestClient extends KbbClientPrices {

}
