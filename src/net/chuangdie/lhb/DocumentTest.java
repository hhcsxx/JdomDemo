package net.chuangdie.lhb;

import java.io.File;
import java.util.List;

import org.jdom.Attribute;
import org.jdom.Comment;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DocumentTest
{
	private XMLOutputter out = null;

	@Before
	public void init()
	{
		out = new XMLOutputter();
	}

	@After
	public void destroy()
	{
		if (out != null)
		{
			out = null;
		}
		System.gc();
	}

	private void print(Document doc)
	{
		System.out.println(out.outputString(doc));
	}

	private void fail(Object o)
	{
		if (o != null)
		{
			System.out.println(o);
		}
	}

	@Test
	public void createDoc()
	{
		Document doc = null;
		doc = new Document(new Element("root"));
		print(doc);

		doc = new Document(new Element("root").setText("this is a root el"));
		print(doc);

		Element root = new Element("root");
		root.setText("this is a root el");
		root.setAttribute("id", "110");
		doc.setRootElement(root);
		fail("method 3: \n" + out.outputString(doc));

		doc = new Document();
		doc.addContent(new Element("root").setText("this is a root el"));
		fail("method 4: \n" + out.outputString(doc));

		fail(doc.toString());
	}

	@Test
	public void createXMLDoc()
	{
		Document doc = new Document();

		Element car = new Element("car");
		car.setAttribute("vin", "123fhg5869705iop90");
		car.addContent(new Comment("Description of a car"));

		Element make = new Element("make").setText("Toyota");
		Element model = new Element("model").setText("Celica");
		Element year = new Element("year").setText("1997");
		Element color = new Element("color").setText("green");
		Element license = new Element("license").setText("1ABC234");
		license.setAttribute("state", "CA");

		car.addContent(make);
		car.addContent(model);
		car.addContent(year);
		car.addContent(color);
		car.addContent(license);

		doc.setRootElement(car);

		print(doc);
	}

	@Test
	public void readXMLContent()
	{
		SAXBuilder builder = new SAXBuilder();
		Document doc;
		try
		{
			doc = builder.build(new File("file/disk.xml"));
			Element rootEl = doc.getRootElement();
			// 获得所有子元素
			List<Element> list = rootEl.getChildren();

			for (Element el : list)
			{
				// 获取name属性值
				String name = el.getAttributeValue("name");
				// 获取子元素capacity文本值
				String capacity = el.getChildText("capacity");
				// 获取子元素directories文本值
				String directories = el.getChildText("directories");
				String files = el.getChildText("files");

				System.out.println("磁盘信息:");
				System.out.println("分区盘符:" + name);
				System.out.println("分区容量:" + capacity);
				System.out.println("目录数:" + directories);
				System.out.println("文件数:" + files);
				System.out.println("-----------------------------------");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 递归返回指定格式的“--”
	 */
	private String format(int i)
	{
		String temp = "";
		if (i > 0)
		{
			temp += "--";
			i--;
			temp += format(i);
		}
		return temp;
	}

	/**
	 * <b>function:</b>显示当前节点所有Element的属性信息
	 * 
	 * @author hoojo
	 * @createDate 2011-8-4 下午06:10:53
	 * @param el
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private String getAttrInfo(Element el)
	{
		List<Attribute> attrs = el.getAttributes();
		return getAttrInfo(attrs);
	}

	/**
	 * <b>function:</b>显示属性信息
	 * 
	 * @author hoojo
	 * @createDate 2011-8-9 下午03:52:59
	 * @param attrs
	 * @return
	 */
	private String getAttrInfo(List<Attribute> attrs)
	{
		StringBuilder info = new StringBuilder();
		for (Attribute attr : attrs)
		{
			info.append(attr.getName()).append("=").append(attr.getValue()).append(", ");
		}
		if (info.length() > 0)
		{
			return "[" + info.substring(0, info.length() - 2) + "]";
		}
		return "";
	}

	/**
	 * <b>function:</b>递归显示文档节点元素信息
	 * 
	 * @author hoojo
	 * @createDate 2011-8-4 下午05:56:34
	 * @param i
	 * @param list
	 */
	@SuppressWarnings("unchecked")
	private void print(int i, List<Element> list)
	{
		i++;
		for (Element el : list)
		{
			List<Element> childs = el.getChildren();
			if (childs.size() > 0)
			{
				fail(format(i) + el.getName() + "  " + getAttrInfo(el));
				print(i, childs);
			} else
			{
				fail(format(i) + el.getName() + ":" + el.getText() + "  " + getAttrInfo(el));
			}
		}
	}

}
