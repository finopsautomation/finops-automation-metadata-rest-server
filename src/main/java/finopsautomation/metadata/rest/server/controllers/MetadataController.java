package finopsautomation.metadata.rest.server.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import finopsautomation.metadata.model.ProviderTypeEnum;
import finopsautomation.metadata.model.account.Account;
import finopsautomation.metadata.model.account.AccountDefinition;
import finopsautomation.metadata.model.portfolio.Portfolio;
import finopsautomation.metadata.model.portfolio.PortfolioDefinition;
import finopsautomation.metadata.services.MetadataManagementService;
import finopsautomation.metadata.services.model.GrantRevokePortfolioAccessRequest;
import finopsautomation.metadata.services.model.GrantRevokePortfolioAccessResponse;
import finopsautomation.metadata.services.model.QueryAccountRequest;
import finopsautomation.metadata.services.model.QueryPortfolioRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

/**
 * Spring REST endpoint for management of metadata
 */
@RestController
@RequestMapping("/metadata")
public class MetadataController {
	@Autowired
	private MetadataManagementService metadataService;

	@Operation(summary = "Find accounts with optional filter criteria", tags = "Accounts")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Account.class)))),
			@ApiResponse(responseCode = "400", description = "Invalid values") })
	@GetMapping(value = "/accounts", produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<Account> getAccounts(
			@RequestParam(name = "accountName",required = false) String accountNameFilter,
			@RequestParam(name = "friendlyAccountName",required = false) String friendlyAccountNameFilter,
			@RequestParam(name = "providerType",required = false) ProviderTypeEnum providerTypeFilter,
//			@RequestParam(name = "regionID",required = false) String regionIDFilter,
//	        @RequestParam(name = "serviceID",required = false) String serviceIDFilter,
			@RequestParam(name = "billingAccountID",required = false) String billingAccountIDFilter,
	        @RequestParam(name = "linkedAccountID",required = false) String linkedAccountIDFilter){
		QueryAccountRequest queryRequest = new QueryAccountRequest();
		
		queryRequest.setAccountNameFilter(accountNameFilter);
		queryRequest.setFriendlyAccountNameFilter(friendlyAccountNameFilter);
		queryRequest.setBillingAccountIDFilter(billingAccountIDFilter);
		queryRequest.setLinkedAccountIDFilter(linkedAccountIDFilter);
		queryRequest.setProviderTypeFilter(providerTypeFilter);
//		queryRequest.setRegionIDFilter(regionIDFilter);
//		queryRequest.setServiceIDFilter(serviceIDFilter);
		
		return metadataService.findAccounts(queryRequest);
	}

	@Operation(summary = "Find account by ID", tags = "Accounts")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = Account.class))),
			@ApiResponse(responseCode = "400", description = "Invalid ID supplied", content = @Content),
			@ApiResponse(responseCode = "404", description = "Account not found", content = @Content)
	})
	@GetMapping(value = "/accounts/{accountID}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Account> getAccountById(String accountID) {
		return metadataService.findAccountById(accountID)
			.map(ResponseEntity::ok)
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));
	}
	
	@Operation(summary = "Create an account", tags = "Accounts")
	@ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Successful operation")})
	@PostMapping(value = "/accounts", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AccountDefinition> createAccountDefinition(@RequestBody @Valid AccountDefinition request) {
		Optional<AccountDefinition> newDefinition = metadataService.createAccountDefinition(request);
		
        if (newDefinition.isPresent()) {
        	return new ResponseEntity<>(newDefinition.get(), HttpStatus.CREATED);
        } else {
        	return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
	}
	
	// -----------------------------------------------------------------------------------------------------------------------------
	
	@Operation(summary = "Find portfolios with optional filter criteria", tags = "Portfolios")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Portfolio.class)))),
			@ApiResponse(responseCode = "400", description = "Invalid values") })
	@GetMapping(value = "/portfolios", produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<Portfolio> getPortfolios(
			@RequestParam(name = "portfolioName",required = false) String portfolioNameFilter){
		QueryPortfolioRequest queryRequest = new QueryPortfolioRequest();
		
		queryRequest.setPortfolioNameFilter(portfolioNameFilter);
		
		return metadataService.findPortfolios(queryRequest);
	}
	
	@Operation(summary = "Find portfolio by ID",tags = "Portfolios")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = Portfolio.class))),
			@ApiResponse(responseCode = "400", description = "Invalid ID supplied", content = @Content),
			@ApiResponse(responseCode = "404", description = "Account not found", content = @Content)
	})
	@GetMapping(value = "/portfolios/{portfolioID}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Portfolio> getPortfolioById(String portfolioID) {
		return metadataService.findPortfolioById(portfolioID)
			.map(ResponseEntity::ok)
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));
	}
	
	@Operation(summary = "Create a portfolio", tags = "Portfolios")
	@ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Successful operation")})
	@PostMapping(value = "/portfolios", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PortfolioDefinition> createPortfolioDefinition(@RequestBody @Valid PortfolioDefinition request) {
		Optional<PortfolioDefinition> newDefinition = metadataService.createPortfolioDefinition(request);
		
        if (newDefinition.isPresent()) {
        	return new ResponseEntity<>(newDefinition.get(), HttpStatus.CREATED);
        } else {
        	return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
	}
	
	@Operation(summary = "Grant account access to a portfolio", tags = "Portfolios")
	@ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Successful operation")})
	@PostMapping(value = "/portfolios/{portfolioID}/accounts/{accountID}")
	public ResponseEntity<PortfolioDefinition> grantPortfolioAccount(String portfolioID, String accountID) {
		GrantRevokePortfolioAccessResponse resp = metadataService.grantPortfolioAccount(new GrantRevokePortfolioAccessRequest(portfolioID, accountID));
		
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Operation(summary = "Revoke account access to a portfolio", tags = "Portfolios")
	@ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Successful operation")})
	@DeleteMapping(value = "/portfolios/{portfolioID}/accounts/{accountID}")
	public ResponseEntity<PortfolioDefinition> revokePolioAccount(String portfolioID, String accountID) {
		GrantRevokePortfolioAccessResponse resp = metadataService.revokePortfolioAccount(new GrantRevokePortfolioAccessRequest(portfolioID, accountID));
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
