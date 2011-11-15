package com.hornmicro.ui.editor

import org.eclipse.jface.text.rules.EndOfLineRule
import org.eclipse.jface.text.rules.IPredicateRule
import org.eclipse.jface.text.rules.IToken
import org.eclipse.jface.text.rules.RuleBasedPartitionScanner
import org.eclipse.jface.text.rules.Token


class EditorPartitionScanner extends RuleBasedPartitionScanner {
    public static final String COMMENT = "comment"
    public static final String[] TYPES = { COMMENT }
  
    public EditorPartitionScanner() {
      super();
  
      // Create the token for comment partitions
      IToken comment = new Token(COMMENT);
  
      // Set the rule--anything from # to the end of the line is a comment
      setPredicateRules([ new EndOfLineRule("#", comment)] as IPredicateRule[])
    }
}
