package com.devsuperior.hrpayroll.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devsuperior.hrpayroll.entities.Payment;
import com.devsuperior.hrpayroll.entities.Worker;
import com.devsuperior.hrpayroll.feignclientes.WorkerFeignClient;

@Service
public class PaymentService {
	
	@Autowired
	private WorkerFeignClient workerFeignClient;
	

	public Payment getPayment(long workerId, int days) {		
		
		// Usa o .getBody() para pegar o corpo do responseEntity que é o retorno do endpoint findById do serviuço hr-worker
		Worker worker = workerFeignClient.findById(workerId).getBody();
				
		
		return new Payment(worker.getName(), worker.getDailyIncome(), days);
	}

}
