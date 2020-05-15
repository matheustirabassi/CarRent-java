package model.services;

import model.entities.CarRental;
import model.entities.Invoice;

public class RentalService {
	private Double pricePerHour;
	private Double pricePerDay;
	private BrazilTaxService brazilTaxService;
	
	public RentalService() {
		
	};
	public RentalService(Double pricePerHour, Double pricePerDay, BrazilTaxService brazilTaxService) {
		this.pricePerHour = pricePerHour;
		this.pricePerDay = pricePerDay;
		this.brazilTaxService = brazilTaxService;
	}

	public Double getPricePerHour() {
		return pricePerHour;
	}

	public void setPricePerHour(Double pricePerHour) {
		this.pricePerHour = pricePerHour;
	}

	public Double getPricePerDay() {
		return pricePerDay;
	}

	public void setPricePerDay(Double pricePerDay) {
		this.pricePerDay = pricePerDay;
	}

	public BrazilTaxService getBrazilTaxService() {
		return brazilTaxService;
	}

	public void setBrazilTaxService(BrazilTaxService brazilTaxService) {
		this.brazilTaxService = brazilTaxService;
	}
	public void processInvoice(CarRental carRental) {
		long t1 = carRental.getStart().getTime();
		long t2 = carRental.getFinish().getTime();
		double time = (double)(t2 - t1)/1000/60/60;
		double basicPayment;
		if(time <= 12) {
			basicPayment = Math.ceil(time) * pricePerHour;
		}
		else {
			basicPayment = Math.ceil(time/24) * pricePerDay;
		}
		double tax = brazilTaxService.tax(basicPayment);
		carRental.setInvoice(new Invoice(basicPayment, tax));
	}
	
	
}
