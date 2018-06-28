package Utilities;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XML_Writer {

	public static void createXML(String fileLocation) throws Exception {

		try {

			ExcelUtils.setExcelFile(fileLocation);

			// Excel.ColumnDictionary();
			// System.out.println(Excel.getCellData(2, 0));//col , row
			// System.out.println(Excel.getCellData("Flag", 0));
			// System.out.println(Excel.GetCell("Flag"));

			// initialization of drivers

			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory
					.newDocumentBuilder();
			Document document = documentBuilder.newDocument();

			// get the flagged cells with value="Y" from them Excel
			// Excel.GetFlaggedMethods("Flag");
			// Get the number of parameter to be created in XML
			int totalrow = ExcelUtils.getRowCount("Grid_Setup");
			// System.out.println(totalrow);

			// Creating root
			Element rootElementTest = document.createElement("suite");
			rootElementTest.setAttribute("name", "Selenium_test_suite");
			rootElementTest.setAttribute("parallel", "tests");
			rootElementTest.setAttribute("thread-count", "5");
			document.appendChild(rootElementTest);

			// Type the dynamic Group test and classes
			int startrow = 1;
			int startcol = 0;

			for (int paramrow = startrow; paramrow < totalrow; paramrow++) {// loop
																			// first
																			// for
																			// paremeter
																			// rows

				String Flag = ExcelUtils.getCellData(paramrow, 4, "Grid_Setup");// identify
																				// flag
																				// column

				if (Flag.equalsIgnoreCase("Y")) {

					// get values from excel

					String Testurl = ExcelUtils.getCellData(paramrow, startcol,
							"Grid_Setup");
					// System.out.println(Testurl);
					String Testport = ExcelUtils.getCellData(paramrow,
							startcol + 1, "Grid_Setup");
					// System.out.println(Testport);
					String Testnode = Testurl + ":".concat(Testport);
					// System.out.println(Testnode);
					String Platform = ExcelUtils.getCellData(paramrow,
							startcol + 2, "Grid_Setup");
					String Testbrowser = ExcelUtils.getCellData(paramrow,
							startcol + 3, "Grid_Setup");
					// System.out.println(Testbrowser);
					// create test class

					Element testname = document.createElement("test");
					testname.setAttribute("name", "Suite_".concat(Platform)
							+ "_".concat(Testbrowser));
					rootElementTest.appendChild(testname);

					Element param = document.createElement("parameter");
					param.setAttribute("name", "browser");
					param.setAttribute("value", Testbrowser);
					testname.appendChild(param);

					Element param1 = document.createElement("parameter");
					param1.setAttribute("name", "nodeURL");
					param1.setAttribute("value", Testnode);
					testname.appendChild(param1);

					Element param2 = document.createElement("parameter");
					param2.setAttribute("name", "platform");
					param2.setAttribute("value", Platform);
					testname.appendChild(param2);

					// create classes
					Element classes = document.createElement("classes");
					testname.appendChild(classes);

					// create class
					Element class1 = document.createElement("class");
					class1.setAttribute("name", "Executor.GridInitiator");
					classes.appendChild(class1);

					// for testfilepath column
					// String TestSheetName=Excel.getCellData(startcol+2, row);

				}// execute flag=yes if loop

			}// param for loop

			// Generate the file
			FileWriter fstream = new FileWriter("./testng.xml");
			BufferedWriter out = new BufferedWriter(fstream);

			// StreamResult result1 = new StreamResult(new File("test.xml"));

			// Generate the required XML output file
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(document);

			// Prints all the Generated XML code in the File Object
			StreamResult result = new StreamResult(fstream);
			transformer.transform(source, result);

			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(
					"{http://xml.apache.org/xslt}indent-amount", "2");

			Log.info("XML File Created Successfully");
			// System.out.println("File created sucessfully!");
			// close the generated file
			out.close();

		} catch (DOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();

		}

	}
}
