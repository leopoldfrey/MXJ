
public class Test
{
	public static void main(String[] args)
	{
		String def = "max.jvm.option -Djava.library.path=";
		String path = "/Users/leo/Desktop/";
		String appname = "Entropie.app/Contents/";
		String rest = "eesupport/s";
		System.out.println("def:"+def.length()+" path:"+path.length()+" appname:"+appname.length()+" rest:"+rest.length());
		System.out.println("total:"+(def.length()+path.length()+appname.length()+rest.length())+" max:"+(path.length()+rest.length()));
	}
}
