/*
 * Created on 2017-03-29 ( Date ISO 2017-03-29 - Time 10:21:24 )
 * Generated by Telosys ( http://www.telosys.org/ ) version 3.0.0
 */
package org.demo.data.record.listitem;

import org.demo.data.record.BookOrderItemRecord;
import org.demo.commons.ListItem;

public class BookOrderItemListItem implements ListItem {

	private final String value ;
	private final String label ;
	
	public BookOrderItemListItem(BookOrderItemRecord bookOrderItem) {
		super();

		this.value = ""
			 + bookOrderItem.getBookOrderId()
			 + "|"  + bookOrderItem.getBookId()
		;

		//TODO : Define here the attributes to be displayed as the label
		this.label = bookOrderItem.toString();
	}

	@Override
	public String getValue() {
		return value;
	}

	@Override
	public String getLabel() {
		return label;
	}

}
