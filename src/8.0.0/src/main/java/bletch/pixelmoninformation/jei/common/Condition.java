package bletch.pixelmoninformation.jei.common;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.ParametersAreNonnullByDefault;

import bletch.pixelmoninformation.utils.TextUtils;

@ParametersAreNonnullByDefault
public class Condition {
	public static final Map<Condition, Condition> reverse = new LinkedHashMap<>();

    public static final Condition affectedByLooting = new Condition("gui.condition.affectedByLooting.name");
    
    public static final Condition burning = new Condition("gui.condition.burning.name");
    
    protected String text;

    public Condition() {
    }

    public Condition(String text) {
        this.text = text;
    }

    public Condition(String text, Condition opposite) {
        this(text);
        
        reverse.put(opposite, this);
        reverse.put(this, opposite);
    }

    @Override
    public String toString() {
        return TextUtils.translate(text);
    }

}
