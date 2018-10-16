package com.apap.tutorial6.service;

import java.util.Optional;
import static org.junit.Assert.*;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.apap.tutorial6.model.CarModel;
import com.apap.tutorial6.repository.CarDb;
import com.apap.tutorial6.service.CarService;
import com.apap.tutorial6.service.CarServiceImpl;

@RunWith(SpringRunner.class)
public class CarServiceTest {

	@Autowired
	private CarService carService;

	@MockBean
	private CarDb carDb;

	@TestConfiguration
	static class CarServiceTestContextConfiguration {
		@Bean 
		public CarService carService() {
			return new CarServiceImpl();
		}
	}

	@Test
	public void whenValidType_thenCarShouldBeFound() {
		
		CarModel car = new CarModel();
		car.setBrand("MV Agusta");
		car.setType("F4 RC");
		car.setPrice(new Long("1500000000"));
		car.setAmount(13);
		Optional<CarModel> carModel = Optional.of(car);
		Mockito.when(carDb.findByType(carModel.get().getType())).thenReturn(carModel);

		Optional<CarModel> found = carService.getCarDetailByType(car.getType());

		assertThat(found, Matchers.notNullValue());
		assertThat(found.get().getType(), Matchers.equalTo(car.getType()));
	}
}
