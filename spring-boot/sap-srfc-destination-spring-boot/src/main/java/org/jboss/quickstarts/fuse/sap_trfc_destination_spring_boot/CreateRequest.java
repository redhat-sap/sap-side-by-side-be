/**
 * Copyright 2020 Red Hat, Inc.
 * 
 * Red Hat licenses this file to you under the Apache License, version
 * 2.0 (the "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.  See the License for the specific language governing
 * permissions and limitations under the License.
 * 
 */
package org.jboss.quickstarts.fuse.sap_trfc_destination_spring_boot;

import org.apache.camel.Exchange;
import org.fusesource.camel.component.sap.SapTransactionalRfcDestinationEndpoint;
import org.fusesource.camel.component.sap.model.rfc.Structure;
import org.jboss.quickstarts.fuse.types.FlightCustomer;

/**
 * A {@link CreateRequest} is a processor bean which builds a request to the CreateFromData method of the FlightCustomer BAPI
 * to create a flight customer record in SAP for the passed in values.
 * 
 */
public class CreateRequest {

	public void createRequest(Exchange exchange) throws Exception {
		FlightCustomer customer = exchange.getMessage().getMandatoryBody(FlightCustomer.class);

		// Create a request message from the endpoint to the CreateFromData method of the FlightCustomer BAPI
		SapTransactionalRfcDestinationEndpoint endpoint = exchange.getContext().getEndpoint("sap-trfc-destination:quickstartDest:BAPI_FLCUST_CREATEFROMDATA", SapTransactionalRfcDestinationEndpoint.class);
		Structure request = endpoint.createRequest();
		
		// Add customer data to the request for customer record.
		Structure customerData = request.get("CUSTOMER_DATA", Structure.class);
		customerData.put("CUSTNAME", customer.getCustomerName());
		customerData.put("FORM", customer.getForm());
		customerData.put("STREET", customer.getStreet());
		customerData.put("POSTCODE", customer.getPostcode());
		customerData.put("CITY", customer.getCity());
		customerData.put("COUNTR", customer.getCountry());
		customerData.put("PHONE", customer.getPhone());
		customerData.put("EMAIL", customer.getEmail());
		customerData.put("CUSTTYPE", customer.getCustType());
		customerData.put("DISCOUNT", customer.getDiscount());
		customerData.put("LANGU", customer.getLanguage());
		
		// Set the request in in the body of the exchange's message.
		exchange.getIn().setBody(request);
	}

}
