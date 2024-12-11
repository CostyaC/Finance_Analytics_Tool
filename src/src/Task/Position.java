package Task;

public class Position {
	
private int id;
private int quantity;
private int transaction_costs;
private int instrument_id;

private String open_date;
private String close_date;
private String open_price;
private String close_price;
private String instrument_currency;
private String open_transaction_type;
private String close_transaction_type;


public int getId() {
	
	return id;
}


public void setId(int id) {
	
	this.id = id;
}


public int getQuantity() {
	
	return quantity;
}


public void setQuantity(int quantity) {
	
	this.quantity = quantity;
}


public int getTransaction_costs() {
	
	return transaction_costs;
}


public void setTransaction_costs(int transaction_costs) {
	
	this.transaction_costs = transaction_costs;
}


public int getInstrument_id() {
	
	return instrument_id;
}


public void setTool_id(int tool_id) {
	
	this.instrument_id = tool_id;
}


public String getOpen_date() {
	
	return open_date;
}


public void setOpen_date(String open_date) {
	
	this.open_date = open_date;
}


public String getClose_date() {
	
	return close_date;
}

public void setClose_date(String close_date) {
	
	this.close_date = close_date;
}


public String getOpen_price() {
	
	return open_price;
}


public void setOpen_price(String open_price) {
	
	this.open_price = open_price;
}


public String getClose_price() {
	
	return close_price;
}


public void setClose_price(String close_price) {
	
	this.close_price = close_price;
}


public String getInstrument_currency() {
	
	return instrument_currency;
}


public void setTool_currency(String tool_currency) {
	
	
	this.instrument_currency = tool_currency;
}


public String getOpen_transaction_type() {
	
	return open_transaction_type;
}


public void setOpen_transaction_type(String open_transaction_type) {
	
	this.open_transaction_type = open_transaction_type;
}


public String getClose_transaction_type() {
	
	return close_transaction_type;
}


public void setClose_transaction_type(String close_transaction_type) {
	
	this.close_transaction_type = close_transaction_type;
}


@Override
public String toString() {
	
	return "Position{" +
            "id=" + id +
            ", quantity=" + quantity +
            ", transaction_costs=" + transaction_costs +
            ", tool_id=" + instrument_id +
            ", open_date='" + open_date + '\'' +
            ", close_date='" + close_date + '\'' +
            ", open_price='" + open_price + '\'' +
            ", close_price='" + close_price + '\'' +
            ", tool_currency='" + instrument_currency + '\'' +
            ", open_transaction_type='" + open_transaction_type + '\'' +
            ", close_transaction_type='" + close_transaction_type + '\'' +
            '}';

}



}
