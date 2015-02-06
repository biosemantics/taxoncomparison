package edu.arizona.biosemantics.taxoncomparison.log;

import edu.arizona.biosemantics.common.log.AbstractStringifyInjection;
import edu.arizona.biosemantics.common.log.IPrintable;

public aspect StringifyInjection extends AbstractStringifyInjection {

	declare parents : edu.arizona.biosemantics.taxoncomparison.* implements IPrintable;
	declare parents : edu.arizona.biosemantics.taxoncomparison.io..* implements IPrintable;
	declare parents : edu.arizona.biosemantics.taxoncomparison.model..* implements IPrintable;
	declare parents : edu.arizona.biosemantics.taxoncomparison.comparison..* implements IPrintable;

}