public class UC4OOPS{
	public static void main(String[] args){
		String[] lines = new String[7];
		 lines[0]= String.join(" ","    ***   " , "    ***   " , "***** " , "  ***** ");
		 lines[1]= String.join(" "," **    ** " , " **    ** " , "*   **" , " **     ");
		 lines[2]= String.join(" ","**      **" , "**      **" , "*   **" , "**      ");
		 lines[3]= String.join(" ","**      **" , "**      **" , "*  ** " , " *****  ");
		 lines[4]= String.join(" ","**      **" , "**      **" , "***   " , "     ** ");
		 lines[5]= String.join(" "," **    ** " , " **    ** " , "*     " , " **  ** ");
		 lines[6]= String.join(" ","   ***    " , "   ***    " , "*     " , "  ***** ");
		 
		for(int i=0;i<7;i++){
			System.out.println(lines[i]);
		}
	
		}
	
	}