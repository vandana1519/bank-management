package com.springboot.bankingmanagement.contoller.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.bankingmanagement.controller.BankController;
import com.springboot.bankingmanagement.exception.InsufficientAccountBalanceException;
import com.springboot.bankingmanagement.exception.InvalidAccountException;
import com.springboot.bankingmanagement.model.Account;
import com.springboot.bankingmanagement.model.Customer;
import com.springboot.bankingmanagement.model.MoneyTransaction;
import com.springboot.bankingmanagement.model.Statement;
import com.springboot.bankingmanagement.service.BankService;

@WebMvcTest(value = BankController.class)
class BankControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private BankService bankService;

	static Account account = new Account();
	static Customer customer = new Customer();
	static List<Statement> stmt = new ArrayList<>();
	MoneyTransaction moneyTransaction = new MoneyTransaction(1000d, 100L, 101L);

	private ObjectMapper objectMapper = new ObjectMapper();

	@BeforeAll
	public static void setUp() {
		account.setAccountBalance(5000d);
		account.setAccountNumber(1000L);
		account.setUpdatedAt(new Date(System.currentTimeMillis()));
		customer.setCustomerName("Vandana");
		customer.setState("Maharashtra");
		customer.setAccount(account);
		Statement statement = new Statement();
		statement.setAccountNumber(1000L);
		statement.setDate(new Date(System.currentTimeMillis()));
		statement.setDepositAmount(100d);
		statement.setStatementId(1l);
		statement.setTransactionId(12345);
		statement.setWithdrawalAmount(0d);
		stmt.add(statement);
		Statement statement1 = new Statement();
		statement1.setAccountNumber(1000L);
		statement1.setDate(new Date(System.currentTimeMillis()));
		statement1.setDepositAmount(100d);
		statement1.setStatementId(2l);
		statement1.setTransactionId(12345);
		statement1.setWithdrawalAmount(0d);
		stmt.add(statement1);

	}

	@Test
	void createAccountTestSuccess() throws Exception {
		
		Mockito.when(bankService.createAccount(customer)).thenReturn(customer);
		String customerJson = "{\"customerName\":\"Vandana\",\"state\":\"Maharashtra\",\"account\":{\"accountBalance\":10000,\"updatedAt\":\"\"}}";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/customer").accept(MediaType.APPLICATION_JSON)
				.content(customerJson).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
	}

	@Test
	void getStatementTest() throws Exception {

		Mockito.when(bankService.getStatement(1000L, "02", "2021")).thenReturn(stmt);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/statement/{accountNumber}", 1000L)
				.accept(MediaType.APPLICATION_JSON).param("month", "02").param("year", "2021");

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());

	}

	@Test
	void transferFundTestSuccess() throws Exception {

		Mockito.doNothing().when(bankService).transferFund(100L, 101L, 1000d);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/transferFund").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(moneyTransaction));

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());

	}

	@Test
	@ExceptionHandler(InsufficientAccountBalanceException.class)
	@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
	void transferFundTestInsufficientAccountBalanceException() throws Exception {

		Mockito.doThrow(InsufficientAccountBalanceException.class).when(bankService).transferFund(100L, 101L, 1000d);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/transferFund").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(moneyTransaction));

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		assertEquals(HttpStatus.EXPECTATION_FAILED.value(), result.getResponse().getStatus());

	}

	@Test
	@ExceptionHandler(InvalidAccountException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	void transferFundTestInvalidAccountException() throws Exception {

		Mockito.doThrow(InvalidAccountException.class).when(bankService).transferFund(100L, 101L, 1000d);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/transferFund").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(moneyTransaction));

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());

	}

}
