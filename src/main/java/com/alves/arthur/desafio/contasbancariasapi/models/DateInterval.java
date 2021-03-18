package com.alves.arthur.desafio.contasbancariasapi.models;

import java.util.Date;

import lombok.Data;

@Data
public class DateInterval {

	private Date currentDate;
	
	private Date pastDate;
}
