package edu.mdc.cop2805c.assignment2;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 *
 * @author ndelessy
 */
public class ClassTester {

    private final boolean isAbstractRequired;
    private final boolean isInterfaceRequired;
    private final String[] requiredMethodNames;
    private final Class<?>[][] requiredMethodParameterTypes; 
    private final String[] requiredFieldNames;
    private final Class<?>[] requiredFieldtypes;

    public ClassTester(boolean isAbstractRequired, 
                        boolean isInterfaceRequired, 
                        String[] requiredMethodNames, 
                        Class<?>[][] requiredMethodParameterTypes, 
                        String[] requiredFieldNames, 
                        Class<?>[] requiredFieldtypes) {
        this.isAbstractRequired = isAbstractRequired;
        this.isInterfaceRequired = isInterfaceRequired;
        this.requiredMethodNames = requiredMethodNames;
        this.requiredMethodParameterTypes = requiredMethodParameterTypes;
        this.requiredFieldNames = requiredFieldNames;
        this.requiredFieldtypes = requiredFieldtypes;
    }

    public boolean testClass(Class<?> clazz) throws NoSuchMethodException, NoSuchFieldException {
        //Modifiers
        int modifiers = clazz.getModifiers();
        if (isAbstractRequired && !Modifier.isAbstract(modifiers)) {
            return false;
        }
        if (isInterfaceRequired && !Modifier.isInterface(modifiers)) {
            return false;
        }

        
        //methods                                                     // getMethods():all the public methods of the class or interface, including those declared by the class or interface itself and those inherited from superclasses and interfaces.
        for (int i = 0; i < requiredMethodNames.length; i++) {        // getDeclaredMethods(): all the methods declared by the class or interface represented by this Class object, including public, protected, default (package) access, and private methods, but excluding inherited methods 
            Method method = clazz.getMethod(requiredMethodNames[i], requiredMethodParameterTypes[i]); 
        }

        //fields
        for(int i = 0; i < requiredFieldNames.length; i++){
             Field field = clazz.getDeclaredField(requiredFieldNames[i]);
             if(!field.getType().equals(requiredFieldtypes[i]))
                 return false;
        }
        
        return true;
    }
}
