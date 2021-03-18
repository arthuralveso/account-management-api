package com.alves.arthur.desafio.contasbancariasapi.models.dtos;

import java.util.Date;

import lombok.Data;

@Data
public class DateInterval {

	private Date currentDate;
	
	private Date pastDate;
}
