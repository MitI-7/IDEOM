package com.github.MitI_7;

import com.intellij.openapi.util.JDOMExternalizable;
import com.intellij.openapi.util.DefaultJDOMExternalizer;
import com.intellij.openapi.util.WriteExternalException;
import com.intellij.openapi.util.InvalidDataException;
import com.intellij.openapi.components.ApplicationComponent;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;


public class IDEOMConfig implements ApplicationComponent, JDOMExternalizable{
    public boolean useWallPaper = false;
    public String imagePath = "";
    public float imageOpacity = 0.2f;

    @NotNull
    public String getComponentName() {
        return "IDEOMConfig";
    }

    public void initComponent() {
    }

    public void disposeComponent() {
    }

    public void writeExternal(Element element) throws WriteExternalException {
        DefaultJDOMExternalizer.writeExternal(this, element);
    }

    public void readExternal(Element element) throws InvalidDataException {
        DefaultJDOMExternalizer.readExternal(this, element);
    }


}
