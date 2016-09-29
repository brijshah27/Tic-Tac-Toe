import java.awt.Color;
import java.awt.Label;



public class Design
{
	boolean active;
	int desx;
	int desy;
	int size;
	Label Line[]=new Label[4];
	
	public Design(int x,int y,int s)
	{
		desx=x;
		desy=y;
		size=s;
		for(int i=0;i<4;i++)
		{
			Line[i]=new Label();
			Line[i].setBackground(new Color(0,255,255));
		}
		Line[0].setBounds(desx+size,desy,2,3*size);
		Line[1].setBounds(desx+2*size,desy,2,3*size);
		Line[2].setBounds(desx,desy+size,3*size,2);
		Line[3].setBounds(desx,desy+2*size,3*size,2);
		
	}
	
}
