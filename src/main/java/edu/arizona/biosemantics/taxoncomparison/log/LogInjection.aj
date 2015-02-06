package edu.arizona.biosemantics.taxoncomparison.log;

import edu.arizona.biosemantics.common.log.AbstractLogInjection;
import edu.arizona.biosemantics.common.log.ILoggable;

public aspect LogInjection extends AbstractLogInjection {

	declare parents : edu.arizona.biosemantics.taxoncomparison..* implements ILoggable;
}
