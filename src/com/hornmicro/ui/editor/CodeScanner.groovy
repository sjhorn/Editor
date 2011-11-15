package com.hornmicro.ui.editor

import org.eclipse.jface.text.TextAttribute
import org.eclipse.jface.text.rules.IRule
import org.eclipse.jface.text.rules.IToken
import org.eclipse.jface.text.rules.IWhitespaceDetector
import org.eclipse.jface.text.rules.IWordDetector
import org.eclipse.jface.text.rules.RuleBasedScanner
import org.eclipse.jface.text.rules.SingleLineRule
import org.eclipse.jface.text.rules.Token
import org.eclipse.jface.text.rules.WhitespaceRule
import org.eclipse.jface.text.rules.WordRule

class CodeScanner extends RuleBasedScanner {
    
    public CodeScanner(ColorManager colorManager) { 

        // Create the tokens for keywords, strings, and other (everything else)
        IToken keyword = new Token(
            new TextAttribute(colorManager.getColor(ColorManager.KEYWORD))
        )
        IToken other = new Token(
            new TextAttribute(colorManager.getColor(ColorManager.DEFAULT))
        )
        IToken string = new Token(
            new TextAttribute(colorManager.getColor(ColorManager.STRING))
        )

        // Use "other" for default
        setDefaultReturnToken(other);

        // Create the rules
        List rules = new ArrayList();

        // Add rules for strings
        rules.add(new SingleLineRule("\"", "\"", string, '\\' as char ));
        rules.add(new SingleLineRule("'", "'", string, '\\' as char));

        // Add rule for whitespace
        rules.add(new WhitespaceRule(new IWhitespaceDetector() {
            public boolean isWhitespace(char c) {
                return Character.isWhitespace(c);
            }
        }))

        // Add rule for keywords, and add the words to the rule
        WordRule wordRule = new WordRule(new WordDetector())
        Syntax.KEYWORDS.each {
            wordRule.addWord(it, keyword)
        }
        rules.add(wordRule)

        setRules(rules as IRule[]);
    }
}

public class Syntax {
    static final String[] KEYWORDS = [
        "abstract",
        "as",
        "assert",
        "boolean",
        "break",
        "byte",
        "case",
        "catch",
        "char",
        "class",
        "const",
        "continue",
        "def",
        "default",
        "do",
        "double",
        "else",
        "enum",
        "extends",
        "false",
        "final",
        "finally",
        "float",
        "for",
        "goto",
        "if",
        "implements",
        "import",
        "in",
        "instanceof",
        "int",
        "interface",
        "long",
        "native",
        "new",
        "null",
        "package",
        "private",
        "protected",
        "public",
        "return",
        "short",
        "static",
        "strictfp",
        "super",
        "switch",
        "synchronized",
        "this",
        "threadsafe",
        "throw",
        "throws",
        "transient",
        "true",
        "try",
        "void",
        "volatile",
        "while"
    ]
}

public class WordDetector implements IWordDetector {
    public boolean isWordStart(char c) {
        return Syntax.KEYWORDS.find { it[0] == c }
    }

    public boolean isWordPart(char c) {
        return (c as String) =~ /\w/
        //return Syntax.KEYWORDS.find { it.contains(c as String) } 
    }
}