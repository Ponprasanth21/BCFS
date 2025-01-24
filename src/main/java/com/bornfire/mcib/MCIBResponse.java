package com.bornfire.mcib;

public class MCIBResponse {

	private Mcib mcib;

    public Mcib getMcib ()
    {
        return mcib;
    }

    public void setMcib (Mcib mcib)
    {
        this.mcib = mcib;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [mcib = "+mcib+"]";
    }
}
