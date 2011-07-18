package com.hornmicro.syntaxhighlight

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.rules.IWordDetector;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.IWhitespaceDetector;
import org.eclipse.jface.text.rules.SingleLineRule;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WhitespaceRule;
import org.eclipse.jface.text.rules.WordRule;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.swt.SWT;

import com.hornmicro.TextEditor;

class CodeScanner extends RuleBasedScanner {
    
    public CodeScanner() { 
        ColorManager cm = TextEditor.APP.getColorManager();

        // Create the tokens for keywords, strings, and other (everything else)
        IToken keyword = new Token(
            new TextAttribute(cm.getColor(ColorManager.KEYWORD))
        )
        IToken other = new Token(
            new TextAttribute(cm.getColor(ColorManager.DEFAULT))
        )
        IToken string = new Token(
            new TextAttribute(cm.getColor(ColorManager.STRING))
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
        WordRule wordRule = new WordRule(new WordDetector(), other)
        int i = 0
        for (int n = Syntax.KEYWORDS.length; i < n; i++) {
            wordRule.addWord(Syntax.KEYWORDS[i], keyword)
        }
        rules.add(wordRule)

        IRule[] result = new IRule[rules.size()];
        rules.toArray(result);
        setRules(result);
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
        int i = 0
        for (int n = Syntax.KEYWORDS.length; i < n; i++) {
            if (c == ((String) Syntax.KEYWORDS[i]).charAt(0)) {
                return true;
            }
        }
        return false;
    }

    public boolean isWordPart(char c) {
        int i = 0
        int n
        for (n = Syntax.KEYWORDS.length; i < n; i++) {
            if (((String) Syntax.KEYWORDS[i]).indexOf(c as String) != -1) {
                return true
            }
        }
        return false
    }
}