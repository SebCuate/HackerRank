package javaStuff;

import implement.InvocaValidatorCFD33;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.activation.DataHandler;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.net.ssl.HttpsURLConnection;
import javax.sql.DataSource;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.rpc.holders.ByteArrayHolder;
import javax.xml.rpc.holders.StringHolder;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.ws.http.HTTPException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.ssl.Base64;
import org.mail.send.Mail;
import org.mail.send.Tools;
import org.xml.sax.SAXException;

import utilerias.Comparo;
import utilerias.Referencia;
import ValidacionSAT.CFDI;
import ValidacionSAT.ConsultaSAT;
import ValidacionSAT.ExceptionValidacion;
import ValidacionSAT.ReadQR;
import ValidacionSAT.Validaciones;
import XML.XML_RUC;
import bbva.com.mx.archiving.Recupera;
import bbva.com.mx.controller.SellaTimbra;
import bbva.com.mx.object.Atributo;
import bbva.com.mx.object.Nodo;
import bbva.com.mx.utils.Utilerias;

import com.adobe.holders.BLOBHolder;
import com.bbva.mx.dyds.SF01;
import com.bbva.tools.enviaxml.ParametrosProxy;
import com.bbva.tools.utilerias.Comprobante;
import com.ibm.ws.management.util.FileUtils;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;
import com.mx.bbva.implement.CallFtp;
import com.mx.bbva.implement.Ejecuta;
import com.mx.bbva.ssh.ExecutionSSH;

public class WSGetCFD {

	private String sFiliales = "";


	// LOCAL

//	 public static String PATHPROPERTIES ="C:/opt/querysFacturasCFDI.properties";
//	 
//	 public static String CONFIG_VALIDADOR="C:/export/PACK33/CONFIG/CFD33/cfd33.properties";

	// SERVER
	public static String PATHPROPERTIES = "/home/jemed/querysFacturasCFDI.properties";
	public static String CONFIG_VALIDADOR= "/home/jemed/Validador/PACK33/CONFIG/CFD33/cfd33.properties";
	
	public String[] CMD = {
			"/bin/sh",
			"-c",
			"cd @PATH; ls @PATH; zip -P @PASSWORD @ARCHIVO.zip *.pdf *.xml # ask zip to encryptt" }; // 194
																										// y
																										// 195
	// public String [] CMD = { "/bin/sh", "-c",
	// "cd @PATH; ls -l; 7z a -p@PASSWORD @ARCHIVO.zip *.pdf *.xml" };//132 y
	// 148
	public static String PATHJARVALSAT = "/home/jemed/testValidacion/TestValidacionSat.jar ";// 194
																								// y


	// QA && PROD
	public static String PROPERTIES_HIPOTECARIA = "/home/jemed/ServicioMEDCHipotecaria.properties";

	public String[] getPeriodosFac(String rfcEmisor, String rfcReceptor,
			String opcion, StringHolder EXIT_ESTATUS) {
		String[] periodos = null;
		GetXML getXML = new GetXML();
		CallQuery CALLQ = getXML.loadProperties(PATHPROPERTIES);
		try {

			//if (rfcReceptor != null && !rfcReceptor.isEmpty()) {
				System.out.println("RfcReceptor*******: " + rfcReceptor);
				periodos = getXML.getPeriodos(rfcEmisor, rfcReceptor, opcion,
						CALLQ);
				if (periodos != null && periodos.length > 0) {
					EXIT_ESTATUS.value = "OK";
				} else {
					EXIT_ESTATUS.value = "No se encontraron periodos";
				}
			/*} else {
				EXIT_ESTATUS.value = "El rfcReceptor no puede ser nulo o vacio";
			}*/
			/*
			 * }else{ EXIT_ESTATUS.value =
			 * "El rfcEmisor no puede ser nulo o vacio"; }
			 */
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			EXIT_ESTATUS.value = "No se pudieron cargar los periodos.";
			ex.printStackTrace();
		}
		System.out.println("Termino....");
		return periodos;
	}

	public String getDataFac(String rfcEmisor, String rfcReceptor,
			String periodo, String opcion, StringHolder EXIT_ESTATUS) {
		String respuesta = null;
		GetXML getXML = new GetXML();
		try {
			if (rfcReceptor != null && !rfcReceptor.isEmpty()) {
				System.out.println("*****rfcReceptor: " + rfcReceptor);
				if (periodo != null && !periodo.isEmpty()) {
					System.out.println("*****periodo: " + periodo);
					CallQuery CALLQ = getXML.loadProperties(PATHPROPERTIES);
					respuesta = getXML.getDataFact(rfcEmisor, rfcReceptor,
							periodo, opcion, CALLQ);
					if (respuesta != null && !respuesta.isEmpty()) {
						EXIT_ESTATUS.value = "OK";
						System.out.println("Termino..... ");
					} else {
						EXIT_ESTATUS.value = "No se encontraron datos";
						System.out.println("No se encontraron datos");
					}
				} else {
					if (opcion != null && !opcion.isEmpty()) {
						if (opcion.substring(3, 6).toUpperCase().equals("CON")) {
							CallQuery CALLQ = getXML
									.loadProperties(PATHPROPERTIES);
							respuesta = getXML.getDataFact(rfcEmisor,
									rfcReceptor, periodo, opcion, CALLQ);
							if (respuesta != null && !respuesta.isEmpty()) {
								EXIT_ESTATUS.value = "OK";
								System.out.println("Termino..... ");
							} else {
								EXIT_ESTATUS.value = "No se encontraron datos";
								System.out.println("No se encontraron datos");
							}
						} else {
							EXIT_ESTATUS.value = "El periodo no puede ser nulo o vacio";
							System.out
									.println("El periodo no puede ser nulo o vacio");
						}
					} else {
						EXIT_ESTATUS.value = "El periodo no puede ser nulo o vacio";
						System.out
								.println("El periodo no puede ser nulo o vacio");
					}
				}
			} else {
				EXIT_ESTATUS.value = "El rfcReceptor no puede ser nulo o vacio";
				System.out.println("El rfcReceptor no puede ser nulo o vacio");
			}
			/*
			 * }else{ EXIT_ESTATUS.value =
			 * "El rfcEmisor no puede ser nulo o vacio"; }
			 */
		} catch (Exception ex) {
			EXIT_ESTATUS.value = "No se pudieron cargar los datos.";
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
		return respuesta;
	}

	public byte[] getZipFac(String empresaEmisora, String rfc, String folioCFD,
			String fechaTimbrado, String fechaInicio, String fechaFinal,
			String periodo, String tipoArchivo, String referencias,
			String opcion, String aplicativo, StringHolder EXIT_ESTATUS) {

		byte[] document = null;
		System.out.println("Entro");
		GetXML getXML = new GetXML();
		List<Archivo> archivos = null;
		boolean ejecutaXML = false;
		boolean ejecutaPDF = false;
		CallQuery CALLQ = getXML.loadProperties(PATHPROPERTIES);
		try {
			if ((empresaEmisora == null || empresaEmisora.isEmpty())
					&& (rfc == null || rfc.isEmpty())
					&& (folioCFD == null || folioCFD.isEmpty())
					&& (fechaTimbrado == null || fechaTimbrado.isEmpty())
					&& (fechaInicio == null || fechaInicio.isEmpty())
					&& (fechaFinal == null || fechaFinal.isEmpty())
					&& (periodo == null || periodo.isEmpty())
					&& (tipoArchivo == null || tipoArchivo.isEmpty())
					&& (opcion == null || opcion.isEmpty())
					&& (aplicativo == null || aplicativo.isEmpty())
					&& (referencias == null || referencias.isEmpty())) {
				EXIT_ESTATUS.value = "Se deben de colocar los criterios de busqueda";
				System.out
						.println("Se deben de colocar los criterios de busqueda");
			} else {
				System.out.println("ejecuta getZipFac");
				if (tipoArchivo != null && !tipoArchivo.isEmpty()) {
					System.out.println("tipoArchivo: " + tipoArchivo);
					if (tipoArchivo.trim().equals("0")) {
						ejecutaXML = true;
						ejecutaPDF = false;
					} else if (tipoArchivo.trim().equals("1")) {
						ejecutaXML = false;
						ejecutaPDF = true;
					} else if (tipoArchivo.trim().equals("2")) {
						ejecutaXML = true;
						ejecutaPDF = true;
					} else {
						ejecutaXML = true;
						ejecutaPDF = true;
					}
				} else {
					ejecutaXML = true;
					ejecutaPDF = true;
				}
				if (referencias != null && !referencias.isEmpty()) {
					System.out.println("referencias: " + referencias);
					imprimeCriterios(empresaEmisora, rfc, folioCFD,
							fechaTimbrado, fechaInicio, fechaFinal, periodo,
							tipoArchivo, referencias, opcion, aplicativo);
					if (verificaReferencias(referencias)) {
						List<Referencia> ref = getRefereencias(referencias);
						if (ref != null && ref.size() > 0) {
							archivos = new ArrayList<Archivo>();
							String xmlDato = "";
							for (Referencia refe : ref) {
								System.out.println("Referencia****: "
										+ refe.getReferencia());
								String avFilial = refe.getReferencia().trim()
										.substring(9, 12);
								System.out.println("avFilial: " + avFilial);
								String xmlCon = "";
								if (avFilial != null && !avFilial.isEmpty()
										&& avFilial.equals("CON")) {

									String referencia = getXML
											.getReferenciaDeConstancias(refe
													.getReferencia().trim(),
													refe.getPeriodo().trim(),
													CALLQ);

									System.out.println("referencia respuesta: "
											+ referencia);
									if (referencia != null
											&& !referencia.isEmpty()) {
										xmlDato = getXML.getXMLDeReferencias(
												referencia, refe.getPeriodo(),
												CALLQ);
										xmlCon = getXML.getXMLDeReferencias(
												refe.getReferencia(),
												refe.getPeriodo(), CALLQ);
										refe.setReferencia(referencia);
										xmlDato = convertAcentosXML(xmlDato);
									}
								} else {
									xmlDato = getXML.getXMLDeReferencias(
											refe.getReferencia(),
											refe.getPeriodo(), CALLQ);
								}
								if (xmlDato != null && !xmlDato.isEmpty()) {
									if (avFilial != null && !avFilial.isEmpty()
											&& avFilial.equals("CON")) {
										archivos.add(new Archivo(refe
												.getPeriodo(), xmlDato, refe
												.getReferencia(), refe
												.getPeriodo(), xmlCon));
									} else {
										archivos.add(new Archivo(refe
												.getPeriodo(), xmlDato, refe
												.getReferencia(), refe
												.getPeriodo()));
									}
								}
							}
							if (archivos != null && archivos.size() > 0) {
								document = procesaArchivos(archivos,
										ejecutaPDF, ejecutaXML, EXIT_ESTATUS);
								System.out.println("Document: "
										+ document.length);
							} else {
								System.out
										.println("No se encontraron archivos con esos criterios.");
								EXIT_ESTATUS.value = "No se encontraron archivos con esos criterios.";
							}
						}
					} else {
						System.out.println("Las referencias son incorrectas");
						EXIT_ESTATUS.value = "Las referencias son incorrectas";
					}
				} else {
					boolean ejecutaPeriodo = false;
					if (periodo != null && !periodo.isEmpty()) {
						ejecutaPeriodo = true;
					} else {
						if (folioCFD != null && !folioCFD.isEmpty()) {
							ejecutaPeriodo = true;
						} else {
							System.out
									.println("El periodo no puede ir nulo o vacio, o el folioCFD");
							EXIT_ESTATUS.value = "El periodo no puede ir nulo o vacio, o el folioCFD";
						}
					}
					if (ejecutaPeriodo) {
						imprimeCriterios(empresaEmisora, rfc, folioCFD,
								fechaTimbrado, fechaInicio, fechaFinal,
								periodo, tipoArchivo, referencias, opcion,
								aplicativo);
						boolean ejecuta = true;
						boolean valRango = true;
						if ((fechaInicio != null && !fechaInicio.isEmpty())
								&& (fechaFinal != null && !fechaFinal.isEmpty())) {
							if (validaFecha(fechaInicio, EXIT_ESTATUS)) {
								if (validaFecha(fechaFinal, EXIT_ESTATUS)) {
									if (validaRangoFechas(fechaInicio,
											fechaFinal, EXIT_ESTATUS)) {
										ejecuta = true;
										valRango = true;
									} else {
										ejecuta = false;
										valRango = false;
									}
								} else {
									ejecuta = false;
								}
							} else {
								ejecuta = false;
							}
						} else {
							ejecuta = true;
						}
						if ((fechaTimbrado != null && !fechaTimbrado.isEmpty())) {
							if (validaFecha(fechaTimbrado, EXIT_ESTATUS)) {
								ejecuta = true;
							} else {
								ejecuta = false;
							}
						}
						if (ejecuta) {

							archivos = getXML.getXMLMasivos(empresaEmisora,
									rfc, folioCFD, fechaTimbrado, fechaInicio,
									fechaFinal, periodo, opcion, aplicativo,
									CALLQ);
							if (archivos != null && archivos.size() > 0) {
								document = procesaArchivos(archivos,
										ejecutaPDF, ejecutaXML, EXIT_ESTATUS);
							} else {
								System.out
										.println("No se encontraron archivos con esos criterios.");
								EXIT_ESTATUS.value = "No se encontraron archivos con esos criterios.";
							}
						} else {
							if (valRango) {
								System.out.println("Error en formato de fecha");
								EXIT_ESTATUS.value = "Error en formato de fecha";
							}
						}
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			EXIT_ESTATUS.value = "No se pudieron cargar los archivos.";
			e.printStackTrace();
		}
		return document;
	}

	public byte[] getZipFacSat(String empresaEmisora, String rfc,

	String folioCFD, String fechaTimbrado, String fechaInicio,
			String fechaFinal, String periodo, String tipoArchivo,
			String referencias, String opcion, String aplicativo,
			StringHolder EXIT_ESTATUS) {

		byte[] document = null;
		System.out.println("Entro");
		GetXML getXML = new GetXML();
		List<Archivo> archivos = null;
		boolean ejecutaXML = false;
		boolean ejecutaPDF = false;
		CallQuery CALLQ = getXML.loadProperties(PATHPROPERTIES);
		try {
			if ((empresaEmisora == null || empresaEmisora.isEmpty())
					&& (rfc == null || rfc.isEmpty())
					&& (folioCFD == null || folioCFD.isEmpty())
					&& (fechaTimbrado == null || fechaTimbrado.isEmpty())
					&& (fechaInicio == null || fechaInicio.isEmpty())
					&& (fechaFinal == null || fechaFinal.isEmpty())
					&& (periodo == null || periodo.isEmpty())
					&& (tipoArchivo == null || tipoArchivo.isEmpty())
					&& (opcion == null || opcion.isEmpty())
					&& (aplicativo == null || aplicativo.isEmpty())
					&& (referencias == null || referencias.isEmpty())) {
				EXIT_ESTATUS.value = "Se deben de colocar los criterios de busqueda";
				System.out
						.println("Se deben de colocar los criterios de busqueda");
			} else {
				System.out.println("ejecuta getZipFac");
				if (tipoArchivo != null && !tipoArchivo.isEmpty()) {
					System.out.println("tipoArchivo: " + tipoArchivo);
					if (tipoArchivo.trim().equals("0")) {
						ejecutaXML = true;
						ejecutaPDF = false;
					} else if (tipoArchivo.trim().equals("1")) {
						ejecutaXML = false;
						ejecutaPDF = true;
					} else if (tipoArchivo.trim().equals("2")) {
						ejecutaXML = true;
						ejecutaPDF = true;
					} else {
						ejecutaXML = true;
						ejecutaPDF = true;
					}
				} else {
					ejecutaXML = true;
					ejecutaPDF = true;
				}
				if (referencias != null && !referencias.isEmpty()) {
					System.out.println("referencias: " + referencias);
					imprimeCriterios(empresaEmisora, rfc, folioCFD,
							fechaTimbrado, fechaInicio, fechaFinal, periodo,
							tipoArchivo, referencias, opcion, aplicativo);
					if (verificaReferencias(referencias)) {
						List<Referencia> ref = getRefereencias(referencias);
						if (ref != null && ref.size() > 0) {
							archivos = new ArrayList<Archivo>();
							String xmlDato = "";
							for (Referencia refe : ref) {
								System.out.println("Referencia****: "
										+ refe.getReferencia());
								String avFilial = refe.getReferencia().trim()
										.substring(9, 12);
								System.out.println("avFilial: " + avFilial);
								String xmlCon = "";
								if (avFilial != null && !avFilial.isEmpty()
										&& avFilial.equals("CON")) {
									String referencia = getXML
											.getReferenciaDeConstancias(refe
													.getReferencia().trim(),
													refe.getPeriodo().trim(),
													CALLQ);
									System.out.println("referencia respuesta: "
											+ referencia);
									if (referencia != null
											&& !referencia.isEmpty()) {
										xmlDato = getXML.getXMLDeReferencias(
												referencia, refe.getPeriodo(),
												CALLQ);
										xmlCon = getXML.getXMLDeReferencias(
												refe.getReferencia(),
												refe.getPeriodo(), CALLQ);
										refe.setReferencia(referencia);
										xmlDato = convertAcentosXML(xmlDato);
									}
								} else {
									xmlDato = getXML.getXMLDeReferencias(
											refe.getReferencia(),
											refe.getPeriodo(), CALLQ);
								}
								if (xmlDato != null && !xmlDato.isEmpty()) {
									if (avFilial != null && !avFilial.isEmpty()
											&& avFilial.equals("CON")) {
										archivos.add(new Archivo(refe
												.getPeriodo(), xmlDato, refe
												.getReferencia(), refe
												.getPeriodo(), xmlCon));
									} else {
										archivos.add(new Archivo(refe
												.getPeriodo(), xmlDato, refe
												.getReferencia(), refe
												.getPeriodo()));
									}
								}
							}
							if (archivos != null && archivos.size() > 0) {
								document = procesaArchivosSinAdenda(archivos,
										ejecutaPDF, ejecutaXML, EXIT_ESTATUS);
								System.out.println("Document: "
										+ document.length);
							} else {
								System.out
										.println("No se encontraron archivos con esos criterios.");
								EXIT_ESTATUS.value = "No se encontraron archivos con esos criterios.";
							}
						}
					} else {
						System.out.println("Las referencias son incorrectas");
						EXIT_ESTATUS.value = "Las referencias son incorrectas";
					}
				} else {
					boolean ejecutaPeriodo = false;
					if (periodo != null && !periodo.isEmpty()) {
						ejecutaPeriodo = true;
					} else {
						if (folioCFD != null && !folioCFD.isEmpty()) {
							ejecutaPeriodo = true;
						} else {
							System.out
									.println("El periodo no puede ir nulo o vacio, o el folioCFD");
							EXIT_ESTATUS.value = "El periodo no puede ir nulo o vacio, o el folioCFD";
						}
					}
					if (ejecutaPeriodo) {
						imprimeCriterios(empresaEmisora, rfc, folioCFD,
								fechaTimbrado, fechaInicio, fechaFinal,
								periodo, tipoArchivo, referencias, opcion,
								aplicativo);
						boolean ejecuta = true;
						boolean valRango = true;
						if ((fechaInicio != null && !fechaInicio.isEmpty())
								&& (fechaFinal != null && !fechaFinal.isEmpty())) {
							if (validaFecha(fechaInicio, EXIT_ESTATUS)) {
								if (validaFecha(fechaFinal, EXIT_ESTATUS)) {
									if (validaRangoFechas(fechaInicio,
											fechaFinal, EXIT_ESTATUS)) {
										ejecuta = true;
										valRango = true;
									} else {
										ejecuta = false;
										valRango = false;
									}
								} else {
									ejecuta = false;
								}
							} else {
								ejecuta = false;
							}
						} else {
							ejecuta = true;
						}
						if ((fechaTimbrado != null && !fechaTimbrado.isEmpty())) {
							if (validaFecha(fechaTimbrado, EXIT_ESTATUS)) {
								ejecuta = true;
							} else {
								ejecuta = false;
							}
						}
						if (ejecuta) {

							archivos = getXML.getXMLMasivos(empresaEmisora,
									rfc, folioCFD, fechaTimbrado, fechaInicio,
									fechaFinal, periodo, opcion, aplicativo,
									CALLQ);
							if (archivos != null && archivos.size() > 0) {
								document = procesaArchivosSinAdenda(archivos,
										ejecutaPDF, ejecutaXML, EXIT_ESTATUS);
							} else {
								System.out
										.println("No se encontraron archivos con esos criterios.");
								EXIT_ESTATUS.value = "No se encontraron archivos con esos criterios.";
							}
						} else {
							if (valRango) {
								System.out.println("Error en formato de fecha");
								EXIT_ESTATUS.value = "Error en formato de fecha";
							}
						}
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			EXIT_ESTATUS.value = "No se pudieron cargar los archivos.";
			e.printStackTrace();
		}
		return document;
	}

	private void imprimeCriterios(String empresaEmisora, String rfc,
			String folioCFD, String fechaTimbrado, String fechaInicio,
			String fechaFinal, String periodo, String tipoArchivo,
			String referencias, String opcion, String aplicativo) {
		if (empresaEmisora != null && !empresaEmisora.isEmpty()) {
			System.out.println("EmpresaEmisora: " + empresaEmisora);
		}
		if (rfc != null && !rfc.isEmpty()) {
			System.out.println("RFC Receptor: " + rfc);
		}
		if (folioCFD != null && !folioCFD.isEmpty()) {
			System.out.println("Folio CFD: " + folioCFD);
		}
		if (fechaTimbrado != null && !fechaTimbrado.isEmpty()) {
			System.out.println("Fecha timbrado: " + fechaTimbrado);
		}
		if (fechaInicio != null && !fechaInicio.isEmpty()) {
			System.out.println("Fecha inicio: " + fechaInicio);
		}
		if (fechaFinal != null && !fechaFinal.isEmpty()) {
			System.out.println("Fecha fin: " + fechaFinal);
		}
		if (periodo != null && !periodo.isEmpty()) {
			System.out.println("Periodo: " + periodo);
		}
		if (tipoArchivo != null && !tipoArchivo.isEmpty()) {
			System.out.println("Tipo archivo: " + tipoArchivo);
		}
		if (referencias != null && !referencias.isEmpty()) {
			System.out.println("Referencias: " + referencias);
		}
		if (opcion != null && !opcion.isEmpty()) {
			System.out.println("Opcion: " + opcion);
		}
		if (aplicativo != null && !aplicativo.isEmpty()) {
			System.out.println("Aplicativo: " + aplicativo);
		}
	}

	private byte[] procesaArchivos(List<Archivo> archivos, boolean ejecutaPDF,
			boolean ejecutaXML, StringHolder EXIT_ESTATUS) throws Exception {
		byte[] respuesta = null;
		String xmls = "";
		String xmlsZip = "";
		File directorio = null;
		File directorioZip = null;
		int cont = 1;
		BufferedWriter bw = null;
		GetXML getxml = new GetXML();
		Properties pr = new Properties();
		Properties sg = new Properties();
		boolean ejecutaGMC = false;
		boolean ejecutaGMCPrin = false;
		Integer eGMC = null;
		String fechaGMC = "";
		String procGMC = "";
		String pathDownLoadGMC = "";
		String pathServer = "";
		String separador = "";
		FileOutputStream archPDF = null;
		String nombrePDF = "";
		String pathGMC = "";
		try {
			pr.load(new FileInputStream(PATHPROPERTIES));
			sg.load(new FileInputStream(pr.getProperty("PATH_GMC")));
			pathGMC = pr.getProperty("PATH_GMC");
			separador = "/";
			eGMC = new Integer(pr.getProperty("EJECUTA_GMC"));
			fechaGMC = pr.getProperty("FECHA_GMC");
			procGMC = pr.getProperty("PROC_GMC");
			if (eGMC != null) {
				if (eGMC.intValue() == 1) {
					ejecutaGMCPrin = true;
				}
			}
			pathDownLoadGMC = sg.getProperty("PATH_LOCAL_OUT_W6");
			pathServer = sg.getProperty("URL_SERVER");
		} catch (Exception e) {

			pr.load(new FileInputStream(
					"C:\\Pruebas\\querysFacturasCFDI.properties")); // PRUEBAS
			sg.load(new FileInputStream(pr.getProperty("PATH_GMC")));
			separador = "\\";

		}
		if (archivos.size() > 1) {
			xmls = FileUtils.createTempDirectory();
			xmlsZip = FileUtils.createTempDirectory();
			// System.out.println("Carpeta archivos: " + xmls);
			// System.out.println("Carpeta zip: " + xmlsZip);
			// System.out.println("Mayor a uno*************");
			directorioZip = new File(xmlsZip);
			directorio = new File(xmls);

			for (Archivo archivo : archivos) {
				try {
					String referencia = archivo.getReferencia();
					if (referencia != null && !referencia.isEmpty()) {
						if (ejecutaGMCPrin) {
							if (!ejecutaGMC(referencia, procGMC, fechaGMC)) {
								ejecutaGMC = false;
							} else {
								ejecutaGMC = true;
							}
						}
						String filial = referencia.substring(6, 12);
						String avFilial = referencia.substring(9, 12);
						if (ejecutaPDF) {
							ByteArrayHolder pdf = new ByteArrayHolder(
									new byte[1024]);
							SF01 oSF01 = new SF01();
							nombrePDF = archivo.getNombre() + "_" + cont
									+ ".pdf";
							System.out.println("nombrePDF: " + nombrePDF);
							if (avFilial.equals("CON")) {
								generaPDF gen = new generaPDF();
								if (ejecutaGMC) {

									pdf.value = gen.getPDFGMC(pathGMC,
											pathDownLoadGMC, pathServer,
											referencia, EXIT_ESTATUS, null,
											null);
									System.out
											.println("Traigo del PDF Zip GMC Fac CON:"
													+ (EXIT_ESTATUS.value
															.trim().equals("") ? "Vengo vacio"
															: EXIT_ESTATUS.value)
													+ ",peso: "
													+ pdf.value.length);
								} else {
									BLOBHolder BLOBHolderPDF = new BLOBHolder();
									gen.getPDF(filial, archivo.getXml()
											.getBytes(), EXIT_ESTATUS,
											BLOBHolderPDF);
									pdf.value = BLOBHolderPDF.value
											.getBinaryData();
									System.out
											.println("Traigo del PDF Zip Fac CON:"
													+ (EXIT_ESTATUS.value
															.trim().equals("") ? "Vengo vacio"
															: EXIT_ESTATUS.value)
													+ ",peso: "
													+ BLOBHolderPDF.value
															.getBinaryData().length);
								}
								if (EXIT_ESTATUS.value.trim().equals("0")) {
									System.out.println("Exit estatus 0.");
									if (pdf.value.length > 0) {
										// System.out.println("xmls: "+xmls);
										// System.out.println("nombrePDF: "+nombrePDF);
										archPDF = new FileOutputStream(xmls
												+ nombrePDF);
										archPDF.write(pdf.value);
										// System.out.println("Se imprime pdf");
										if (archPDF != null) {
											archPDF.close();
											archPDF = null;
										}
									} else {
										System.out
												.println("Error al tratar de generar el pdf.");
										EXIT_ESTATUS.value = "Error al tratar de generar el pdf.";
									}
								} else {
									EXIT_ESTATUS.value = "Error al generar el pdf, "
											+ EXIT_ESTATUS.value;
									System.err
											.println("Error al generar el pdf");
								}
							} else {
								GetXML getXML = new GetXML();
								CallQuery CALLQ = getXML
										.loadProperties(PATHPROPERTIES);
								archivo = getxml.getDataFact(archivo, CALLQ);
								System.out.println("RFC: " + archivo.getRfc());
								System.out.println("NBUUID: "
										+ archivo.getNbuuid());
								System.out.println("FechaEmision: "
										+ archivo.getFechaEmision());
								System.out.println("Referencia: "
										+ archivo.getReferencia());
								String cuae = oSF01.encode(archivo.getRfc()
										.trim(), archivo.getNbuuid().trim(),
										archivo.getFechaEmision().trim(),
										archivo.getReferencia().trim());
								System.out.println("Cuae: " + cuae);
								archivo.setCuae(cuae);
								String miRef = archivo.getReferencia().trim();
								System.out.println("miRef: " + miRef);
								String origen = miRef.substring(6, 12);
								System.out.println("Origen: " + origen);
								System.out.println("EXIT_ESTATUS: "
										+ EXIT_ESTATUS.value.trim());
								StringHolder stringHolder = new StringHolder(
										"-1");
								this.executeInvokePDF(archivo.getRfc(),
										archivo.getNbuuid(),
										archivo.getFechaEmision(),
										archivo.getCuae(), origen,
										stringHolder, pdf);
								System.out
										.println("pdf1: " + pdf.value.length
												+ ",stringHolder:"
												+ stringHolder.value);
								if (pdf.value.length > 0) {
									// if
									// (EXIT_ESTATUS.value.trim().equals("0")) {
									archPDF = new FileOutputStream(xmls
											+ nombrePDF);
									archPDF.write(pdf.value);
									System.out.println("Se imprime pdf");
									if (archPDF != null) {
										archPDF.close();
										archPDF = null;
									}
								} else {
									EXIT_ESTATUS.value = "Error al tratar de generar el pdf.";
									System.out
											.println("Error al tratar de generar el pdf.");
								}
							}
						}

						if (ejecutaXML) {
							String nombreArchivo = archivo.getNombre() + "_"
									+ cont + ".xml";
							File xml = new File(xmls + nombreArchivo);
							bw = new BufferedWriter(new FileWriter(xml));
							if (avFilial.equals("CON")) {
								bw.write(archivo.getXmlConstancia());
							} else {
								bw.write(archivo.getXml());
							}
						}
						cont++;
					}
				} catch (Exception ex) {
					EXIT_ESTATUS.value = "Error al tratar de generar los archivos";
				} finally {
					if (bw != null) {
						bw.close();
					}
					if (archPDF != null) {
						archPDF.close();
					}
				}
			}
			String archivoZip = Utilerias.zipAcuses(xmls, xmlsZip, "xmls.zip",
					"xml,pdf", true);
			File fArchivoZip = new File(archivoZip + "xmls.zip");
			if (fArchivoZip.exists()) {
				InputStream filestream = new FileInputStream(fArchivoZip);
				respuesta = IOUtils.toByteArray(filestream);
				if (respuesta != null) {
					if (filestream != null) {
						filestream.close();
					}
					EXIT_ESTATUS.value = "ZIP";
					fArchivoZip.delete();
					directorio.delete();
					directorioZip.delete();
				} else {
					EXIT_ESTATUS.value = "No se encontraron archivos para generar el archivo zip del arreglo";
				}
			} else {
				EXIT_ESTATUS.value = "No se encontraron archivos para generar el archivo zip";
			}
		} else {
			Archivo archivo = archivos.get(0);
			if (ejecutaPDF && ejecutaXML) {
				System.out.println("Los 2");
				System.out.println("PDF y XML");
				xmls = FileUtils.createTempDirectory();
				xmlsZip = FileUtils.createTempDirectory();
				System.out.println("Carpeta archivos: " + xmls);
				System.out.println("Carpeta zip: " + xmlsZip);
				directorioZip = new File(xmlsZip);
				directorio = new File(xmls);
				ByteArrayHolder pdf = new ByteArrayHolder(new byte[1024]);
				SF01 oSF01 = new SF01();
				String referencia = archivo.getReferencia();
				if (referencia != null && !referencia.isEmpty()) {
					String filial = referencia.substring(6, 12);
					String avFilial = referencia.substring(9, 12);
					nombrePDF = archivo.getNombre() + "_" + cont + ".pdf";
					if (avFilial.equals("CON")) {
						generaPDF gen = new generaPDF();
						// System.out.println("*****************ejecutaGMCPrin Zipfac solo: "
						// + ejecutaGMCPrin);
						if (ejecutaGMCPrin) {
							if (!ejecutaGMC(referencia, procGMC, fechaGMC)) {
								ejecutaGMC = false;
							} else {
								ejecutaGMC = true;
							}
						}
						if (ejecutaGMC) {
							pdf.value = gen.getPDFGMC(pathGMC, pathDownLoadGMC,
									pathServer, referencia, EXIT_ESTATUS, null,
									null);
							System.out
									.println("Traigo del PDF Zipfac GMC solo:"
											+ (EXIT_ESTATUS.value.trim()
													.equals("") ? "Vengo vacio"
													: EXIT_ESTATUS.value)
											+ ",peso: " + pdf.value.length);
						} else {
							BLOBHolder BLOBHolderPDF = new BLOBHolder();
							gen.getPDF(filial, archivo.getXml().getBytes(),
									EXIT_ESTATUS, BLOBHolderPDF);
							pdf.value = BLOBHolderPDF.value.getBinaryData();
							System.out
									.println("Traigo del PDF Zipfac solo:"
											+ (EXIT_ESTATUS.value.trim()
													.equals("") ? "Vengo vacio"
													: EXIT_ESTATUS.value)
											+ ",peso: "
											+ BLOBHolderPDF.value
													.getBinaryData().length);
						}
						if (EXIT_ESTATUS.value.trim().equals("0")) {
							if (pdf.value.length > 0) {
								archPDF = new FileOutputStream(xmls + nombrePDF);
								archPDF.write(pdf.value);
								System.out.println("Se imprime pdf");
								if (archPDF != null) {
									archPDF.close();
									archPDF = null;
								}
							} else {
								System.out
										.println("Error al tratar de generar el pdf.");
							}
						} else {
							EXIT_ESTATUS.value = "Error al generar el pdf, "
									+ EXIT_ESTATUS.value;
							System.err.println("Error al generar el pdf");
						}
					} else {
						GetXML getXML = new GetXML();
						CallQuery CALLQ = getXML.loadProperties(PATHPROPERTIES);
						archivo = getxml.getDataFact(archivo, CALLQ);
						// System.out.println("RFC1: " + archivo.getRfc());
						// System.out.println("NBUUID1: " +
						// archivo.getNbuuid());
						// System.out.println("FechaEmision1: "+archivo.getFechaEmision());
						// System.out.println("Referencia1: "+
						// archivo.getReferencia());
						String cuae = oSF01.encode(archivo.getRfc().trim(),
								archivo.getNbuuid().trim(), archivo
										.getFechaEmision().trim(), archivo
										.getReferencia().trim());
						System.out.println("cuae1: " + cuae);
						archivo.setCuae(cuae);
						// System.out.println("nombrePDF1: " + nombrePDF);
						String miRef = archivo.getReferencia().trim();
						// System.out.println("miRef1: " + miRef);
						String origen = miRef.substring(6, 12);
						// System.out.println("Origen1: " + origen);
						StringHolder stringHolder = new StringHolder("-1");
						this.executeInvokePDF(archivo.getRfc(),
								archivo.getNbuuid(), archivo.getFechaEmision(),
								archivo.getCuae(), origen, stringHolder, pdf);
						// System.out.println("pdf1: "+pdf.value.length+",stringHolder:"+stringHolder.value);
						if (pdf.value.length > 0) {
							// respuesta = pdf.value;
							archPDF = new FileOutputStream(xmls + nombrePDF);
							archPDF.write(pdf.value);
							System.out.println("Se imprime pdf");
							if (archPDF != null) {
								archPDF.close();
								archPDF = null;
							}
						} else {
							System.out
									.println("Error al tratar de generar el pdf.");
						}
					}

					String nombreArchivo = archivo.getNombre() + "_" + cont
							+ ".xml";
					File xml = new File(xmls + nombreArchivo);
					bw = new BufferedWriter(new FileWriter(xml));
					if (avFilial.equals("CON")) {
						bw.write(archivo.getXmlConstancia());
					} else {
						bw.write(archivo.getXml());
					}
					if (bw != null) {
						bw.close();
					}
				}
				System.out.println("Se imprime xml");
				String archivoZip = Utilerias.zipAcuses(xmls, xmlsZip,
						"xmls.zip", "xml,pdf", true);
				File fArchivoZip = new File(archivoZip + "xmls.zip");
				if (fArchivoZip.exists()) {
					InputStream filestream = new FileInputStream(fArchivoZip);
					respuesta = IOUtils.toByteArray(filestream);
					if (respuesta != null) {
						if (filestream != null) {
							filestream.close();
						}
						EXIT_ESTATUS.value = "ZIP";
						fArchivoZip.delete();
						directorio.delete();
						directorioZip.delete();
					} else {
						EXIT_ESTATUS.value = "No se encontraron archivos para generar el archivo zip del arreglo";
					}
				} else {
					EXIT_ESTATUS.value = "No se encontraron archivos para generar el archivo zip";
				}
			} else {
				// System.out.println("PDF o XML individual");
				String referencia = archivo.getReferencia();
				// System.out.println("referencia: " + referencia);
				if (referencia != null && !referencia.isEmpty()) {
					String filial = referencia.substring(6, 12);
					// System.out.println("filial: " + filial);
					String avFilial = referencia.substring(9, 12);
					// System.out.println("avFilial: " + avFilial);
					if (ejecutaPDF) {
						System.out.println("solo pdf");
						ByteArrayHolder pdf = new ByteArrayHolder(
								new byte[1024]);
						SF01 oSF01 = new SF01();

						if (avFilial.equals("CON")) {
							generaPDF gen = new generaPDF();
							if (ejecutaGMCPrin) {
								if (!ejecutaGMC(referencia, procGMC, fechaGMC)) {
									ejecutaGMC = false;
								} else {
									ejecutaGMC = true;
								}
							}
							if (ejecutaGMC) {
								pdf.value = gen.getPDFGMC(pathGMC,
										pathDownLoadGMC, pathServer,
										referencia, EXIT_ESTATUS, null, null);
								System.out
										.println("Traigo del PDF zip GMC fac individual:"
												+ (EXIT_ESTATUS.value.trim()
														.equals("") ? "Vengo vacio"
														: EXIT_ESTATUS.value)
												+ ",peso: " + pdf.value.length);
							} else {
								BLOBHolder BLOBHolderPDF = new BLOBHolder();
								gen.getPDF(filial, archivo.getXml().getBytes(),
										EXIT_ESTATUS, BLOBHolderPDF);
								pdf.value = BLOBHolderPDF.value.getBinaryData();
								System.out
										.println("Traigo del PDF zip fac individual:"
												+ (EXIT_ESTATUS.value.trim()
														.equals("") ? "Vengo vacio"
														: EXIT_ESTATUS.value)
												+ ",peso: "
												+ BLOBHolderPDF.value
														.getBinaryData().length);
							}
							if (pdf.value.length > 0) {// EXIT_ESTATUS.value.trim().equals("0")
								respuesta = pdf.value;
								EXIT_ESTATUS.value = "PDF";
								System.out.println("Se imprime pdf");
							} else {
								EXIT_ESTATUS.value = "Error al generar el pdf";
								System.out
										.println("Error al tratar de generar el pdf.");
							}
							/*
							 * if (EXIT_ESTATUS.value.trim().equals("0")) { if
							 * (pdf.value.length > 0) {
							 * System.out.println("xmls: "+xmls);
							 * System.out.println("nombrePDF: "+nombrePDF);
							 * archPDF = new FileOutputStream(xmls+ nombrePDF);
							 * archPDF.write(pdf.value);
							 * System.out.println("Se imprime pdf"); if (archPDF
							 * != null) { archPDF.close(); archPDF = null; } }
							 * else { System.out.println(
							 * "Error al tratar de generar el pdf."); } } else {
							 * EXIT_ESTATUS.value = "Error al generar el pdf, "+
							 * EXIT_ESTATUS.value;
							 * System.err.println("Error al generar el pdf"); }
							 */
						} else {
							GetXML getXML = new GetXML();
							CallQuery CALLQ = getXML
									.loadProperties(PATHPROPERTIES);
							archivo = getxml.getDataFact(archivo, CALLQ);
							// System.out.println("RFC2: " + archivo.getRfc());
							// System.out.println("NBUUID2: "+
							// archivo.getNbuuid());
							// System.out.println("FechaEmision2: "+
							// archivo.getFechaEmision());
							// System.out.println("Referencia2: "+
							// archivo.getReferencia());
							String cuae = oSF01.encode(archivo.getRfc().trim(),
									archivo.getNbuuid().trim(), archivo
											.getFechaEmision().trim(), archivo
											.getReferencia().trim());
							archivo.setCuae(cuae);
							String miRef = archivo.getReferencia().trim();
							// System.out.println("miRef2: " + miRef);
							String origen = miRef.substring(6, 12);
							// System.out.println("Origen2: " + origen);
							StringHolder stringHolder = new StringHolder("-1");
							this.executeInvokePDF(archivo.getRfc(),
									archivo.getNbuuid(),
									archivo.getFechaEmision(),
									archivo.getCuae(), origen, stringHolder,
									pdf);
							// System.out.println("pdf2: " + pdf.value.length+
							// ",stringHolder2:" + stringHolder.value);
							// System.out.println("EXIT_ESTATUS2: "+
							// EXIT_ESTATUS.value);
							if (pdf.value.length > 0) {// EXIT_ESTATUS.value.trim().equals("0")
								respuesta = pdf.value;
								EXIT_ESTATUS.value = "PDF";
								System.out.println("Se imprime pdf");
							} else {
								EXIT_ESTATUS.value = "Error al generar el pdf";
								System.out
										.println("Error al tratar de generar el pdf.");
							}
						}
					}

					if (ejecutaXML) {
						System.out.println("solo xml");
						// System.out.println("Se imprime xml");
						// System.out.println("xml: "+archivo.getXml());
						if (avFilial.equals("CON")) {
							respuesta = archivo.getXmlConstancia().getBytes();
						} else {
							respuesta = archivo.getXml().getBytes();
						}
						EXIT_ESTATUS.value = "XML";
					}
				}
			}// fin else
		}// fin else

		return respuesta;
	}

	private byte[] procesaArchivosSinAdenda(List<Archivo> archivos,
			boolean ejecutaPDF, boolean ejecutaXML, StringHolder EXIT_ESTATUS)
			throws Exception {
		byte[] respuesta = null;
		String xmls = "";
		String xmlsZip = "";
		File directorio = null;
		File directorioZip = null;
		int cont = 1;
		BufferedWriter bw = null;
		GetXML getxml = new GetXML();
		Properties pr = new Properties();
		Properties sg = new Properties();
		boolean ejecutaGMC = false;
		boolean ejecutaGMCPrin = false;
		Integer eGMC = null;
		String fechaGMC = "";
		String procGMC = "";
		String pathDownLoadGMC = "";
		String pathServer = "";
		String separador = "";
		FileOutputStream archPDF = null;
		String nombrePDF = "";
		String pathGMC = "";
		try {
			pr.load(new FileInputStream(PATHPROPERTIES));
			sg.load(new FileInputStream(pr.getProperty("PATH_GMC")));
			pathGMC = pr.getProperty("PATH_GMC");
			separador = "/";
			eGMC = new Integer(pr.getProperty("EJECUTA_GMC"));
			fechaGMC = pr.getProperty("FECHA_GMC");
			procGMC = pr.getProperty("PROC_GMC");
			if (eGMC != null) {
				if (eGMC.intValue() == 1) {
					ejecutaGMCPrin = true;
				}
			}
			pathDownLoadGMC = sg.getProperty("PATH_LOCAL_OUT_W6");
			pathServer = sg.getProperty("URL_SERVER");
		} catch (Exception e) {

			pr.load(new FileInputStream(
					"C:\\Pruebas\\querysFacturasCFDI.properties")); // PRUEBAS
			sg.load(new FileInputStream(pr.getProperty("PATH_GMC")));
			separador = "\\";

		}
		if (archivos.size() > 1) {
			xmls = FileUtils.createTempDirectory();
			xmlsZip = FileUtils.createTempDirectory();
			// System.out.println("Carpeta archivos: " + xmls);
			// System.out.println("Carpeta zip: " + xmlsZip);
			// System.out.println("Mayor a uno*************");
			directorioZip = new File(xmlsZip);
			directorio = new File(xmls);

			for (Archivo archivo : archivos) {
				try {
					String referencia = archivo.getReferencia();
					if (referencia != null && !referencia.isEmpty()) {
						if (ejecutaGMCPrin) {
							if (!ejecutaGMC(referencia, procGMC, fechaGMC)) {
								ejecutaGMC = false;
							} else {
								ejecutaGMC = true;
							}
						}
						String filial = referencia.substring(6, 12);
						String avFilial = referencia.substring(9, 12);
						if (ejecutaPDF) {
							ByteArrayHolder pdf = new ByteArrayHolder(
									new byte[1024]);
							SF01 oSF01 = new SF01();
							nombrePDF = archivo.getNombre() + "_" + cont
									+ ".pdf";
							System.out.println("nombrePDF: " + nombrePDF);
							if (avFilial.equals("CON")) {
								generaPDF gen = new generaPDF();
								if (ejecutaGMC) {

									pdf.value = gen.getPDFGMC(pathGMC,
											pathDownLoadGMC, pathServer,
											referencia, EXIT_ESTATUS, null,
											null);
									System.out
											.println("Traigo del PDF Zip GMC Fac CON:"
													+ (EXIT_ESTATUS.value
															.trim().equals("") ? "Vengo vacio"
															: EXIT_ESTATUS.value)
													+ ",peso: "
													+ pdf.value.length);
								} else {
									BLOBHolder BLOBHolderPDF = new BLOBHolder();
									gen.getPDF(filial, archivo.getXml()
											.getBytes(), EXIT_ESTATUS,
											BLOBHolderPDF);
									pdf.value = BLOBHolderPDF.value
											.getBinaryData();
									System.out
											.println("Traigo del PDF Zip Fac CON:"
													+ (EXIT_ESTATUS.value
															.trim().equals("") ? "Vengo vacio"
															: EXIT_ESTATUS.value)
													+ ",peso: "
													+ BLOBHolderPDF.value
															.getBinaryData().length);
								}
								if (EXIT_ESTATUS.value.trim().equals("0")) {
									System.out.println("Exit estatus 0.");
									if (pdf.value.length > 0) {
										// System.out.println("xmls: "+xmls);
										// System.out.println("nombrePDF: "+nombrePDF);
										archPDF = new FileOutputStream(xmls
												+ nombrePDF);
										archPDF.write(pdf.value);
										// System.out.println("Se imprime pdf");
										if (archPDF != null) {
											archPDF.close();
											archPDF = null;
										}
									} else {
										System.out
												.println("Error al tratar de generar el pdf.");
										EXIT_ESTATUS.value = "Error al tratar de generar el pdf.";
									}
								} else {
									EXIT_ESTATUS.value = "Error al generar el pdf, "
											+ EXIT_ESTATUS.value;
									System.err
											.println("Error al generar el pdf");
								}
							} else {
								GetXML getXML = new GetXML();
								CallQuery CALLQ = getXML
										.loadProperties(PATHPROPERTIES);
								archivo = getxml.getDataFact(archivo, CALLQ);
								System.out.println("RFC: " + archivo.getRfc());
								System.out.println("NBUUID: "
										+ archivo.getNbuuid());
								System.out.println("FechaEmision: "
										+ archivo.getFechaEmision());
								System.out.println("Referencia: "
										+ archivo.getReferencia());
								String cuae = oSF01.encode(archivo.getRfc()
										.trim(), archivo.getNbuuid().trim(),
										archivo.getFechaEmision().trim(),
										archivo.getReferencia().trim());
								System.out.println("Cuae: " + cuae);
								archivo.setCuae(cuae);
								String miRef = archivo.getReferencia().trim();
								System.out.println("miRef: " + miRef);
								String origen = miRef.substring(6, 12);
								System.out.println("Origen: " + origen);
								System.out.println("EXIT_ESTATUS: "
										+ EXIT_ESTATUS.value.trim());
								StringHolder stringHolder = new StringHolder(
										"-1");
								this.executeInvokePDF(archivo.getRfc(),
										archivo.getNbuuid(),
										archivo.getFechaEmision(),
										archivo.getCuae(), origen,
										stringHolder, pdf);
								System.out
										.println("pdf1: " + pdf.value.length
												+ ",stringHolder:"
												+ stringHolder.value);
								if (pdf.value.length > 0) {
									// if
									// (EXIT_ESTATUS.value.trim().equals("0")) {
									archPDF = new FileOutputStream(xmls
											+ nombrePDF);
									archPDF.write(pdf.value);
									System.out.println("Se imprime pdf");
									if (archPDF != null) {
										archPDF.close();
										archPDF = null;
									}
								} else {
									EXIT_ESTATUS.value = "Error al tratar de generar el pdf.";
									System.out
											.println("Error al tratar de generar el pdf.");
								}
							}
						}

						if (ejecutaXML) {
							String nombreArchivo = archivo.getNombre() + "_"
									+ cont + ".xml";
							File xml = new File(xmls + nombreArchivo);
							bw = new BufferedWriter(new FileWriter(xml));
							if (avFilial.equals("CON")) {

								GetXML getXML = new GetXML();
								CallQuery CALLQ = getXML
										.loadProperties(PATHPROPERTIES);

								System.out.println(CALLQ.getAddenda());

								String[] separaLlaveAddenda = CALLQ
										.getAddenda().split(",");

								String xmlsinaddenda = removeAddenda(
										archivo.getXmlConstancia(),
										separaLlaveAddenda);
								bw.write(xmlsinaddenda);

								// bw.write(archivo.getXmlConstancia());
							} else {

								GetXML getXML = new GetXML();
								CallQuery CALLQ = getXML
										.loadProperties(PATHPROPERTIES);

								System.out.println(CALLQ.getAddenda());

								String[] separaLlaveAddenda = CALLQ
										.getAddenda().split(",");

								String xmlsinaddenda = removeAddenda(
										archivo.getXml(), separaLlaveAddenda);
								bw.write(xmlsinaddenda);
								// bw.write(archivo.getXml());
							}
						}
						cont++;
					}
				} catch (Exception ex) {
					EXIT_ESTATUS.value = "Error al tratar de generar los archivos";
				} finally {
					if (bw != null) {
						bw.close();
					}
					if (archPDF != null) {
						archPDF.close();
					}
				}
			}
			String archivoZip = Utilerias.zipAcuses(xmls, xmlsZip, "xmls.zip",
					"xml,pdf", true);
			File fArchivoZip = new File(archivoZip + "xmls.zip");
			if (fArchivoZip.exists()) {
				InputStream filestream = new FileInputStream(fArchivoZip);
				respuesta = IOUtils.toByteArray(filestream);
				if (respuesta != null) {
					if (filestream != null) {
						filestream.close();
					}
					EXIT_ESTATUS.value = "ZIP";
					fArchivoZip.delete();
					directorio.delete();
					directorioZip.delete();
				} else {
					EXIT_ESTATUS.value = "No se encontraron archivos para generar el archivo zip del arreglo";
				}
			} else {
				EXIT_ESTATUS.value = "No se encontraron archivos para generar el archivo zip";
			}
		} else {
			Archivo archivo = archivos.get(0);
			if (ejecutaPDF && ejecutaXML) {
				System.out.println("Los 2");
				System.out.println("PDF y XML");
				xmls = FileUtils.createTempDirectory();
				xmlsZip = FileUtils.createTempDirectory();
				System.out.println("Carpeta archivos: " + xmls);
				System.out.println("Carpeta zip: " + xmlsZip);
				directorioZip = new File(xmlsZip);
				directorio = new File(xmls);
				ByteArrayHolder pdf = new ByteArrayHolder(new byte[1024]);
				SF01 oSF01 = new SF01();
				String referencia = archivo.getReferencia();
				if (referencia != null && !referencia.isEmpty()) {
					String filial = referencia.substring(6, 12);
					String avFilial = referencia.substring(9, 12);
					nombrePDF = archivo.getNombre() + "_" + cont + ".pdf";
					if (avFilial.equals("CON")) {
						generaPDF gen = new generaPDF();
						// System.out.println("*****************ejecutaGMCPrin Zipfac solo: "
						// + ejecutaGMCPrin);
						if (ejecutaGMCPrin) {
							if (!ejecutaGMC(referencia, procGMC, fechaGMC)) {
								ejecutaGMC = false;
							} else {
								ejecutaGMC = true;
							}
						}
						if (ejecutaGMC) {
							pdf.value = gen.getPDFGMC(pathGMC, pathDownLoadGMC,
									pathServer, referencia, EXIT_ESTATUS, null,
									null);
							System.out
									.println("Traigo del PDF Zipfac GMC solo:"
											+ (EXIT_ESTATUS.value.trim()
													.equals("") ? "Vengo vacio"
													: EXIT_ESTATUS.value)
											+ ",peso: " + pdf.value.length);
						} else {
							BLOBHolder BLOBHolderPDF = new BLOBHolder();
							gen.getPDF(filial, archivo.getXml().getBytes(),
									EXIT_ESTATUS, BLOBHolderPDF);
							pdf.value = BLOBHolderPDF.value.getBinaryData();
							System.out
									.println("Traigo del PDF Zipfac solo:"
											+ (EXIT_ESTATUS.value.trim()
													.equals("") ? "Vengo vacio"
													: EXIT_ESTATUS.value)
											+ ",peso: "
											+ BLOBHolderPDF.value
													.getBinaryData().length);
						}
						if (EXIT_ESTATUS.value.trim().equals("0")) {
							if (pdf.value.length > 0) {
								archPDF = new FileOutputStream(xmls + nombrePDF);
								archPDF.write(pdf.value);
								System.out.println("Se imprime pdf");
								if (archPDF != null) {
									archPDF.close();
									archPDF = null;
								}
							} else {
								System.out
										.println("Error al tratar de generar el pdf.");
							}
						} else {
							EXIT_ESTATUS.value = "Error al generar el pdf, "
									+ EXIT_ESTATUS.value;
							System.err.println("Error al generar el pdf");
						}
					} else {
						GetXML getXML = new GetXML();
						CallQuery CALLQ = getXML.loadProperties(PATHPROPERTIES);
						archivo = getxml.getDataFact(archivo, CALLQ);
						// System.out.println("RFC1: " + archivo.getRfc());
						// System.out.println("NBUUID1: " +
						// archivo.getNbuuid());
						// System.out.println("FechaEmision1: "+archivo.getFechaEmision());
						// System.out.println("Referencia1: "+
						// archivo.getReferencia());
						String cuae = oSF01.encode(archivo.getRfc().trim(),
								archivo.getNbuuid().trim(), archivo
										.getFechaEmision().trim(), archivo
										.getReferencia().trim());
						System.out.println("cuae1: " + cuae);
						archivo.setCuae(cuae);
						// System.out.println("nombrePDF1: " + nombrePDF);
						String miRef = archivo.getReferencia().trim();
						// System.out.println("miRef1: " + miRef);
						String origen = miRef.substring(6, 12);
						// System.out.println("Origen1: " + origen);
						StringHolder stringHolder = new StringHolder("-1");
						this.executeInvokePDF(archivo.getRfc(),
								archivo.getNbuuid(), archivo.getFechaEmision(),
								archivo.getCuae(), origen, stringHolder, pdf);
						// System.out.println("pdf1: "+pdf.value.length+",stringHolder:"+stringHolder.value);
						if (pdf.value.length > 0) {
							// respuesta = pdf.value;
							archPDF = new FileOutputStream(xmls + nombrePDF);
							archPDF.write(pdf.value);
							System.out.println("Se imprime pdf");
							if (archPDF != null) {
								archPDF.close();
								archPDF = null;
							}
						} else {
							System.out
									.println("Error al tratar de generar el pdf.");
						}
					}

					String nombreArchivo = archivo.getNombre() + "_" + cont
							+ ".xml";
					File xml = new File(xmls + nombreArchivo);
					bw = new BufferedWriter(new FileWriter(xml));
					if (avFilial.equals("CON")) {
						GetXML getXML = new GetXML();
						CallQuery CALLQ = getXML.loadProperties(PATHPROPERTIES);

						System.out.println(CALLQ.getAddenda());

						String[] separaLlaveAddenda = CALLQ.getAddenda().split(
								",");

						String xmlsinaddenda = removeAddenda(
								archivo.getXmlConstancia(), separaLlaveAddenda);
						bw.write(xmlsinaddenda);

					} else {
						GetXML getXML = new GetXML();
						CallQuery CALLQ = getXML.loadProperties(PATHPROPERTIES);

						System.out.println(CALLQ.getAddenda());

						String[] separaLlaveAddenda = CALLQ.getAddenda().split(
								",");

						String xmlsinaddenda = removeAddenda(archivo.getXml(),
								separaLlaveAddenda);
						bw.write(xmlsinaddenda);
					}
					if (bw != null) {
						bw.close();
					}
				}
				System.out.println("Se imprime xml");
				String archivoZip = Utilerias.zipAcuses(xmls, xmlsZip,
						"xmls.zip", "xml,pdf", true);
				File fArchivoZip = new File(archivoZip + "xmls.zip");
				if (fArchivoZip.exists()) {
					InputStream filestream = new FileInputStream(fArchivoZip);
					respuesta = IOUtils.toByteArray(filestream);
					if (respuesta != null) {
						if (filestream != null) {
							filestream.close();
						}
						EXIT_ESTATUS.value = "ZIP";
						fArchivoZip.delete();
						directorio.delete();
						directorioZip.delete();
					} else {
						EXIT_ESTATUS.value = "No se encontraron archivos para generar el archivo zip del arreglo";
					}
				} else {
					EXIT_ESTATUS.value = "No se encontraron archivos para generar el archivo zip";
				}
			} else {
				// System.out.println("PDF o XML individual");
				String referencia = archivo.getReferencia();
				// System.out.println("referencia: " + referencia);
				if (referencia != null && !referencia.isEmpty()) {
					String filial = referencia.substring(6, 12);
					// System.out.println("filial: " + filial);
					String avFilial = referencia.substring(9, 12);
					// System.out.println("avFilial: " + avFilial);
					if (ejecutaPDF) {
						System.out.println("solo pdf");
						ByteArrayHolder pdf = new ByteArrayHolder(
								new byte[1024]);
						SF01 oSF01 = new SF01();

						if (avFilial.equals("CON")) {
							generaPDF gen = new generaPDF();
							if (ejecutaGMCPrin) {
								if (!ejecutaGMC(referencia, procGMC, fechaGMC)) {
									ejecutaGMC = false;
								} else {
									ejecutaGMC = true;
								}
							}
							if (ejecutaGMC) {
								pdf.value = gen.getPDFGMC(pathGMC,
										pathDownLoadGMC, pathServer,
										referencia, EXIT_ESTATUS, null, null);
								System.out
										.println("Traigo del PDF zip GMC fac individual:"
												+ (EXIT_ESTATUS.value.trim()
														.equals("") ? "Vengo vacio"
														: EXIT_ESTATUS.value)
												+ ",peso: " + pdf.value.length);
							} else {
								BLOBHolder BLOBHolderPDF = new BLOBHolder();
								gen.getPDF(filial, archivo.getXml().getBytes(),
										EXIT_ESTATUS, BLOBHolderPDF);
								pdf.value = BLOBHolderPDF.value.getBinaryData();
								System.out
										.println("Traigo del PDF zip fac individual:"
												+ (EXIT_ESTATUS.value.trim()
														.equals("") ? "Vengo vacio"
														: EXIT_ESTATUS.value)
												+ ",peso: "
												+ BLOBHolderPDF.value
														.getBinaryData().length);
							}
							if (pdf.value.length > 0) {// EXIT_ESTATUS.value.trim().equals("0")
								respuesta = pdf.value;
								EXIT_ESTATUS.value = "PDF";
								System.out.println("Se imprime pdf");
							} else {
								EXIT_ESTATUS.value = "Error al generar el pdf";
								System.out
										.println("Error al tratar de generar el pdf.");
							}
							/*
							 * if (EXIT_ESTATUS.value.trim().equals("0")) { if
							 * (pdf.value.length > 0) {
							 * System.out.println("xmls: "+xmls);
							 * System.out.println("nombrePDF: "+nombrePDF);
							 * archPDF = new FileOutputStream(xmls+ nombrePDF);
							 * archPDF.write(pdf.value);
							 * System.out.println("Se imprime pdf"); if (archPDF
							 * != null) { archPDF.close(); archPDF = null; } }
							 * else { System.out.println(
							 * "Error al tratar de generar el pdf."); } } else {
							 * EXIT_ESTATUS.value = "Error al generar el pdf, "+
							 * EXIT_ESTATUS.value;
							 * System.err.println("Error al generar el pdf"); }
							 */
						} else {
							GetXML getXML = new GetXML();
							CallQuery CALLQ = getXML
									.loadProperties(PATHPROPERTIES);
							archivo = getxml.getDataFact(archivo, CALLQ);
							 System.out.println("RFC2: " + archivo.getRfc());
							 System.out.println("NBUUID2: "+
							 archivo.getNbuuid());
							 System.out.println("FechaEmision2: "+
							 archivo.getFechaEmision());
							 System.out.println("Referencia2: "+
							 archivo.getReferencia());
							String cuae = oSF01.encode(archivo.getRfc().trim(),
									archivo.getNbuuid().trim(), archivo
											.getFechaEmision().trim(), archivo
											.getReferencia().trim());
							archivo.setCuae(cuae);
							String miRef = archivo.getReferencia().trim();
							System.out.println("miRef2: " + miRef);
							String origen = miRef.substring(6, 12);
							System.out.println("Origen2: " + origen);
							StringHolder stringHolder = new StringHolder("-1");
							this.executeInvokePDF(archivo.getRfc(),
									archivo.getNbuuid(),
									archivo.getFechaEmision(),
									archivo.getCuae(), origen, stringHolder,
									pdf);
							 System.out.println("pdf2: " + pdf.value.length+
							 ",stringHolder2:" + stringHolder.value);
							 System.out.println("EXIT_ESTATUS2: "+
							 EXIT_ESTATUS.value);
							if (pdf.value.length > 0) {// EXIT_ESTATUS.value.trim().equals("0")
								respuesta = pdf.value;
								EXIT_ESTATUS.value = "PDF";
								System.out.println("Se imprime pdf");
							} else {
								EXIT_ESTATUS.value = "Error al generar el pdf";
								System.out
										.println("Error al tratar de generar el pdf.");
							}
						}
					}

					if (ejecutaXML) {
						System.out.println("solo xml");
						// System.out.println("Se imprime xml");
						// System.out.println("xml: "+archivo.getXml());
						if (avFilial.equals("CON")) {

							GetXML getXML = new GetXML();
							CallQuery CALLQ = getXML
									.loadProperties(PATHPROPERTIES);

							System.out.println(CALLQ.getAddenda());

							String[] separaLlaveAddenda = CALLQ.getAddenda()
									.split(",");

							String xmlsinaddenda = removeAddenda(
									archivo.getXmlConstancia(),
									separaLlaveAddenda);
							respuesta = xmlsinaddenda.getBytes();
						} else {
							GetXML getXML = new GetXML();
							CallQuery CALLQ = getXML
									.loadProperties(PATHPROPERTIES);

							System.out.println(CALLQ.getAddenda());

							String[] separaLlaveAddenda = CALLQ.getAddenda()
									.split(",");

							String xmlsinaddenda = removeAddenda(
									archivo.getXml(), separaLlaveAddenda);

							respuesta = xmlsinaddenda.getBytes();
						}
						EXIT_ESTATUS.value = "XML";
					}
				}
			}// fin else
		}// fin else

		return respuesta;
	}

	public static String removeAddenda(String xml, String[] llaveaddenda) {

		String xmlSinAddenda = xml;
		for (int i = 0; i < llaveaddenda.length; i++) {

			if (xml.contains(llaveaddenda[i])) {
				xmlSinAddenda = xml.replaceFirst("<" + llaveaddenda[i]
						+ ".*?</" + llaveaddenda[i] + ">", "");
				break;
			}
		}
		return xmlSinAddenda;
	}

	public String executeInvoke(String serie, String folio, String fEmision,
			String CUAE, String opcion, StringHolder EXIT_ESTATUS) {
		System.out.println("RFC executeInvoke, Antes: " + serie);
		serie = RFCamp(serie);
		System.out.println("RFC executeInvoke, Despues: " + serie);
		EXIT_ESTATUS.value = "";
		Boolean CFDI = Boolean.valueOf(false);
		String rValidaicon = "";
		if ((opcion.equals("0")) || (opcion.equals("1"))) {
			opcion = traigoopcion(opcion);
			rValidaicon = setArgumentos(serie, folio, fEmision, CUAE, opcion);
		} else {
			folio = folio.toUpperCase();
			int lenFolio = folio.length();
			System.out.println(folio + "len:" + lenFolio + " hay -"
					+ folio.contains("-"));
			if ((folio.contains("-")) && (lenFolio == 36)) {
				rValidaicon = setArgumentosCFDI(serie, folio, fEmision, CUAE,
						opcion);
				CFDI = Boolean.valueOf(true);
			} else {
				rValidaicon = setArgumentos(serie, folio, fEmision, CUAE,
						opcion);
				CFDI = Boolean.valueOf(false);
			}
		}
		System.out.println(rValidaicon);
		if (rValidaicon.trim().equals("")) {
			GetXML getXML = new GetXML();
			if ((opcion.equals("0")) || (opcion.equals("1"))) {
				System.out.println("opcion:" + opcion);
			} else {
				System.out.println("antes CFDI:" + CFDI + " validacion:"
						+ rValidaicon);
				if (!CFDI.booleanValue())
					rValidaicon = validaDatos(serie, folio, fEmision, CUAE,
							opcion, CFDI);
				if (CFDI.booleanValue())
					rValidaicon = validaDatosCFDI(serie, folio, fEmision, CUAE,
							opcion, CFDI);
				System.out.println("despues CFDI:" + CFDI + " validacion:"
						+ rValidaicon);
			}
			if (rValidaicon.trim().equals("")) {
				CallQuery CALLQ = getXML.loadProperties(PATHPROPERTIES);// /llamada
																		// a al
																		// properties

				// ************************************************************
				Connection conFAC = null;
				try {
					conFAC = GetDataSource(CALLQ).getConnection();
					rValidaicon = getExisteXML(serie, folio, fEmision, opcion,
							CFDI, CALLQ, getXML, conFAC);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (conFAC != null) {
						try {
							conFAC.close();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				}

				// *************************************************************

				System.out.println("rvalidation: " + rValidaicon);
				if (rValidaicon.trim().equals("1")) {
					String[] Datos = getCd_XML(serie, folio, fEmision, opcion,
							CFDI, CALLQ, getXML);
					String CD_XML = Datos[0];
					System.out.println(CD_XML);
					String valorPrivado = Datos[1];
					if ((!CD_XML.trim().equals(""))
							&& (!valorPrivado.trim().equals(""))) {
						rValidaicon = validateCUEA(serie, folio, fEmision,
								valorPrivado, CUAE, CFDI);
						if (rValidaicon.trim().equals("")) {
							EXIT_ESTATUS.value = "1";
							return getXML.executeInvoke(CD_XML, CALLQ);
						} else {
							EXIT_ESTATUS.value = "0";
						}
						return rValidaicon;
					}
					EXIT_ESTATUS.value = "0";
					return "No se encontro ningÃºn Comprobante Fiscal Digital con los parametros proporcionados(2).";
				}
				EXIT_ESTATUS.value = "0";
				return "No se encontro ningÃºn Comprobante Fiscal Digital con los parametros proporcionados(1)";
			}
			EXIT_ESTATUS.value = "0";
			return rValidaicon;
		}
		EXIT_ESTATUS.value = "0";
		return rValidaicon;
	}

	public void executeInvokePDF(String serie, String folio, String fEmision,
			String CUAE, String opcion, StringHolder EXIT_ESTATUS,
			ByteArrayHolder pdf) {

		System.out.println("RFC executeInvokePDF, Antes : " + serie);
		serie = RFCamp(serie);
		System.out.println("RFC executeInvokePDF, Despues: " + serie);
		Boolean CFDI = Boolean.valueOf(false);
		String rValidaicon = "";
		EXIT_ESTATUS.value = "";
		Properties pr = new Properties();
		Properties sg = new Properties();
		boolean ejecutaGMC = false;
		Integer eGMC = null;
		String fechaGMC = "";
		String procGMC = "";
		String pathDownLoadGMC = "";
		String pathServer = "";
		String separador = "";
		String pathGMC = "";
		boolean ejecutaGMCPrin = false;
		if ((opcion.equals("0")) || (opcion.equals("1"))) {
			opcion = traigoopcion(opcion);
			rValidaicon = setArgumentos(serie, folio, fEmision, CUAE, opcion);
		} else {
			folio = folio.toUpperCase();
			int lenFolio = folio.length();
			System.out.println(folio + "len:" + lenFolio + " hay -"
					+ folio.contains("-"));
			if ((folio.contains("-")) && (lenFolio == 36)) {
				rValidaicon = setArgumentosCFDI(serie, folio, fEmision, CUAE,
						opcion);
				CFDI = Boolean.valueOf(true);
			} else {
				rValidaicon = setArgumentos(serie, folio, fEmision, CUAE,
						opcion);
				CFDI = Boolean.valueOf(false);
			}
		}
		try {
			System.out.println("Empieza rutina GMC");
			pr.load(new FileInputStream(PATHPROPERTIES));
			sg.load(new FileInputStream(pr.getProperty("PATH_GMC")));
			pathGMC = pr.getProperty("PATH_GMC");
			separador = "/";
			eGMC = new Integer(pr.getProperty("EJECUTA_GMC"));
			fechaGMC = pr.getProperty("FECHA_GMC");
			procGMC = pr.getProperty("PROC_GMC");
			if (eGMC != null) {
				if (eGMC.intValue() == 1) {
					System.out.println("Ejecuta GMC");
					ejecutaGMCPrin = true;
				} else {
					System.out.println("Ejecuta Adobe");
				}
			}
			pathDownLoadGMC = sg.getProperty("PATH_LOCAL_OUT_W6");
			pathServer = sg.getProperty("URL_SERVER");
		} catch (Exception e) {
			System.out.println("ERROR GMC");
			e.printStackTrace();
			try {
				pr.load(new FileInputStream(
						"C:\\Pruebas\\querysFacturasCFDI.properties")); // PRUEBAS
				sg.load(new FileInputStream(pr.getProperty("PATH_GMC")));
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			separador = "\\";

		}
		if (rValidaicon.trim().equals("")) {
			GetXML getXML = new GetXML();
			if ((opcion.equals("0")) || (opcion.equals("1"))) {
				System.out.println("opcion:" + opcion);
			} else {
				System.out.println("antes CFDI:" + CFDI + " validacion:"
						+ rValidaicon);
				if (!CFDI.booleanValue())
					rValidaicon = validaDatos(serie, folio, fEmision, CUAE,
							opcion, CFDI);
				if (CFDI.booleanValue())
					rValidaicon = validaDatosCFDI(serie, folio, fEmision, CUAE,
							opcion, CFDI);
				System.out.println("despues CFDI:" + CFDI + " validacion:"
						+ rValidaicon);
			}
			if (rValidaicon.trim().equals("")) {

				CallQuery CALLQ = getXML.loadProperties(PATHPROPERTIES);

				// rValidaicon = getExisteXML(serie, folio, fEmision, opcion,
				// CFDI, CALLQ, getXML);

				// ************************************************************
				Connection conFAC = null;
				Connection conEDC = null;
				try {

					conFAC = GetDataSource(CALLQ).getConnection();
					conEDC = GetDataSourceEDC(CALLQ).getConnection();

					rValidaicon = getExisteXML(serie, folio, fEmision, opcion,
							CFDI, CALLQ, getXML, conFAC);
				} catch (Exception e) {
					e.printStackTrace();
				}

				// *************************************************************

				System.out.println("Paso 3");
				System.out.println("rValidaicon:" + rValidaicon);
				if (rValidaicon.trim().equals("1")) {
					String[] Datos = getCd_XML(serie, folio, fEmision, opcion,
							CFDI, CALLQ, getXML);
					String CD_XML = Datos[0];
					String valorPrivado = Datos[1];
					System.out.println("CD_XML*******: " + CD_XML);
					System.out.println("valorPrivado*******: " + valorPrivado);
					if ((!CD_XML.trim().equals(""))
							&& (!valorPrivado.trim().equals(""))) {
						System.out.println("Paso 4");

						if (ejecutaGMCPrin) {
							System.out.println("ejecutaGMC");
							if (!ejecutaGMC(CD_XML, procGMC, fechaGMC)) {
								ejecutaGMC = false;
								System.out.println("Ejecuta Adobe validacion");
							} else {
								ejecutaGMC = true;
								System.out.println("Ejecuta GMC validacion");
							}
						}
						String filial = CD_XML.substring(9, 12);
						System.out.println("filial*******: " + filial);
						if (filial != null && !filial.isEmpty()
								&& filial.equals("CON")) {
							rValidaicon = validateCUEA(serie, folio, fEmision,
									valorPrivado, CUAE, false);
						} else {
							rValidaicon = validateCUEA(serie, folio, fEmision,
									valorPrivado, CUAE, CFDI);
						}
						System.out.println("validacion: " + rValidaicon);
						if (rValidaicon.trim().equals("")) {
							getXML.loadProperties(PATHPROPERTIES);
							if (filial != null && !filial.isEmpty()
									&& filial.equals("CON")) {
								CD_XML = getXML.getReferenciaDeConstancias(
										CD_XML.trim(), CALLQ);
							}
							generaPDF gen = new generaPDF();
							if (ejecutaGMC) {
								pdf.value = gen.getPDFGMC(pathGMC,
										pathDownLoadGMC, pathServer, CD_XML,
										EXIT_ESTATUS, conFAC, conEDC);
								try {
									conFAC.close();
									conEDC.close();
								} catch (Exception e) {

								}

							} else {
								String xml = getXML
										.executeInvoke(CD_XML, CALLQ);
								String CO = getXML.getCO(CD_XML, CALLQ);
								String Xmlcompleto = "";
								if (filial != null && !filial.isEmpty()
										&& filial.equals("CON")) {
									Xmlcompleto = convertAcentosXML(xml);
								} else {
									Xmlcompleto = agregaInformacionAdicional(
											xml.replace("cfdi:", ""), CO, CUAE,
											CFDI);
								}
								System.out.println("XML Completo: "
										+ Xmlcompleto);
								BLOBHolder BLOBHolderPDF = new BLOBHolder();
								EXIT_ESTATUS = new StringHolder();
								System.out.println("Invoca pdf1");

								System.out.println("Invoca pdf2");
								System.out.println("Opcion: " + opcion);
								gen.getPDF(opcion, Xmlcompleto.getBytes(),
										EXIT_ESTATUS, BLOBHolderPDF);
								System.out
										.println("Traigo del PDF executeInvokePDF:"
												+ (EXIT_ESTATUS.value.trim()
														.equals("") ? "Vengo vacio"
														: EXIT_ESTATUS.value)
												+ ",peso: "
												+ BLOBHolderPDF.value
														.getBinaryData().length);
								if (EXIT_ESTATUS.value.trim().equals("0")) {
									pdf.value = BLOBHolderPDF.value
											.getBinaryData();
								} else {
									EXIT_ESTATUS.value = "Error al generar el pdf, "
											+ EXIT_ESTATUS.value;
									System.err
											.println("Error al generar el pdf");
								}
							}
						} else {
							EXIT_ESTATUS.value = rValidaicon;
						}
					} else {
						EXIT_ESTATUS.value = "No se encontro ningÃºn Comprobante Fiscal Digital con los parametros proporcionados.";
					}
				} else {
					EXIT_ESTATUS.value = "No se encontro ningÃºn Comprobante Fiscal Digital con los parametros proporcionados.";
				}
			} else {
				EXIT_ESTATUS.value = rValidaicon;
			}
		} else {
			EXIT_ESTATUS.value = rValidaicon;
		}
	}

	public void invokecodibide(String RFCdelEmisor, String RFCdelReceptor,
			String Totaldelcomprobante, String UUID, StringHolder EXIT_ESTATUS,
			ByteArrayHolder codibidim) {

		EXIT_ESTATUS.value = "1";
		System.out.println("El servicio invokecodibide no esta disponible");
		
		
	}

	private String agregaInformacionAdicional(String xml, String CO,
			String CUAE, Boolean CFDI) {
		int lenIndex = "total".length();
		System.out.println("lenIndex: " + lenIndex);
		int indexOf = xml.indexOf("total");
		System.out.println("indexOf: " + indexOf);
		int firstOf = xml.indexOf("\"", indexOf + lenIndex) + 1;
		System.out.println("firstOf: " + firstOf);
		int secondOf = xml.indexOf("\"", firstOf);
		System.out.println("secondOf: " + secondOf);
		String total = xml.substring(indexOf, secondOf + 1);
		System.out.println("total: " + total);
		total = total.replace("\"", "").replace("total", "").replace("=", "");
		Comparo getImporteLetra = new Comparo(total);
		StringBuilder _xml = new StringBuilder(xml.substring(0,
				xml.indexOf("</Comprobante>")));
		_xml.append("<InformacionAdicional ");
		_xml.append("cuae=\"").append(CUAE).append("\" ");
		_xml.append("importeLetra=\"")
				.append(getImporteLetra.getImporteLetra()).append("\" ");
		if (!CFDI) {// si es CFD
			_xml.append("cadenaOriginal=\"")
					.append(utf8convert.convertAcentosXML(CO)).append("\" ");
		} else {// Es CFDI
			_xml.append("cadenaOriginal=\"").append("\" ");
		}
		_xml.append("></InformacionAdicional>");
		_xml.append("</Comprobante>");
		// System.out.println("_xml.toString(): " + _xml.toString());
		return _xml.toString();
	}

	public String getCatalogoFiliales() {
		GetCatalogos oFiliales;
		oFiliales = new GetCatalogos();
		CallQuery CALLQ = oFiliales.loadProperties(PATHPROPERTIES);
		sFiliales = oFiliales.GetFiliales(CALLQ, PATHPROPERTIES);
		return this.sFiliales;
	}

	public String edc130(String user, String password) {

		String Regreso = "";

		return Regreso;
	}

	public String getCFDIs(String rfcEmisor, String rfcReceptor,
			String tipoConsulta) {

		rfcEmisor = RFCamp(rfcEmisor);
		rfcReceptor = RFCamp(rfcReceptor);

		// Primero obtenemos el parametro de cuantos periodos se deben mostrar
		// del properties
		GetXML getXML = new GetXML();
		CallQuery CALLQ = getXML.loadProperties(PATHPROPERTIES);// /llamada a al
																// properties

		int numeroPeriodos = 6;// este debes sacarlo del properties
		// Ahora obtenemos el numero de periodos dado el periodo en curso mas 5
		// atras
		System.out.println("numeroPeriodos <<<"+numeroPeriodos);
		String periodos = GetNomina.getPeriodos(Integer
				.parseInt(CALLQ.GETNUMPERIODOS));
		// Aqui haces la consulta dado el qry del properties y reemplazando las
		// variables que traemos
		String consulta = CALLQ.GETPERIODOS;
		consulta = consulta.replace("@periodos", periodos.trim());
		consulta = consulta.replace("@rfcEmisor", rfcEmisor.trim());
		consulta = consulta.replace("@rfcReceptor", rfcReceptor.trim());
	//	System.out.println(consulta); //esta usala solo en pruebas, cuando
		// este produccion quitala
		// de echo todos los prints que tienes q no sirvan como para
		// Debugear, quitalos de una vez
		List<String> cfdis = GetNomina.getCfdi(consulta, tipoConsulta.trim());
		System.out.println("GetNomina.getCfdi >>>>>>"+cfdis.size());
		if (cfdis.size() == 0) {
			return "NO SE ENCONTRARON REGISTROS PARA ESTA CONSULTA.";
		}
		return GetNomina.setFormatListCfdi(cfdis).toString();
	}

	public void setpath(String Path, StringHolder Respuesta) {
		
			Respuesta.value = "El servicio setpath no disponible";
		

	}

	public void setfile(String PathDestino, String NombreArchivo,
			String Archivo, StringHolder Respuesta) {

			System.out.println("El servicio setfile no disponible");
	}

	private boolean ValidoCarpeta(String Path) {
		boolean regreso = false;
		File get = new File(Path);
		if (get.exists()) {
			regreso = true;
		}
		return regreso;
	}

	private String getXml(String volumenID, String objectID, String nameDocument) {

		return getInfo.getXmlCCC(volumenID, objectID, nameDocument);

	}

	public void consultaSat(String Referencia, StringHolder Pdf,
			StringHolder Xml, StringHolder STATUS, StringHolder DETALLE) {

	}

	// Pdf,StringHolder STATUS,StringHolder RESPUESTA)
	public void validacionSat(ByteArrayHolder Xml, ByteArrayHolder Pdf, String[] InfoSat, StringHolder STATUS,
			StringHolder RESPUESTA) throws FileNotFoundException {
		String RfcEmisor = null;
		String RfcReceptor = null;
		String UUID = null;
		String TotalFact = null;
		String FechaEmi = "";
		String SubTotal = "";
		String Descuento = "";
		String TipoComprobante = "";
		String NumDiasPagados = "";
		String NombreEmi = "";
		String FechaPago = "";
		String FechaInicioRelLaboral = "";
		String PeriodicidadPago = "";
		String UbicacionEmpleado = "";
		String Regreso = "";
		String strValida = "";
		boolean isXml = false, array = false;
		Proxy proxyConn = Proxy.NO_PROXY;
		HttpsURLConnection connection = null;
		URL url = null;
		InputStreamReader isr = null;
		BufferedReader in = null;
		String responseString = null;
		String outputString = "";
		StringBuilder validXml = new StringBuilder();
		Properties propFolio;

		if (STATUS == null) {
			STATUS = new StringHolder("");
		}
		if (RESPUESTA == null) {
			RESPUESTA = new StringHolder("");
		}
		if (Pdf == null) {
			Pdf = new ByteArrayHolder();
		}

		String GetXML = TraigoXml(Xml);
		if (InfoSat != null && InfoSat.length >= 4) {
			System.out.println("validacionSat: validaArray");
			RfcEmisor = InfoSat[0].trim();
			RfcReceptor = InfoSat[1].trim();
			TotalFact = InfoSat[2].trim();
			UUID = InfoSat[3].trim();
			array = true;
		} else if (GetXML != null && !GetXML.isEmpty()) {

			System.out.println("GetXML: >>>>>" + new String(Xml.value));
			Boolean openMarket = Boolean.FALSE;
			if (GetXML.contains("openMarket")) {
				System.out.println("Inicia OpenMarket: Recuperar tokens");
				String[] tokens = GetXML.split("\\|");
				String xmlopen = tokens[0];
				System.out.println("xmlopen " + xmlopen);
				GetXML = xmlopen;
				openMarket = Boolean.TRUE;
				Properties propFolio = new Properties(PATHPROPERTIES);
			}

			System.out.println("validacionSat: validaXML");
			String folio = propFolio.getProperty("FOLIO_OPEN_MARKET");
			List Datos = TraigoDatos(GetXML);
			RfcEmisor = Datos.get(0).toString().trim();
			RfcReceptor = Datos.get(1).toString().trim();
			TotalFact = Datos.get(2).toString().trim();
			UUID = Datos.get(3).toString().trim();
			FechaEmi = Datos.get(4).toString().trim();
			SubTotal = Datos.get(5).toString().trim();
			Descuento = Datos.get(6).toString().trim();
			TipoComprobante = Datos.get(7).toString().trim();
			NombreEmi = Datos.get(8).toString().trim();
			NumDiasPagados = Datos.get(9).toString().trim();
			FechaPago = Datos.get(10).toString().trim();
			FechaInicioRelLaboral = Datos.get(11).toString().trim();
			PeriodicidadPago = Datos.get(12).toString().trim();
			UbicacionEmpleado = Datos.get(13).toString().trim();
			if (openMarket) {
				String r = "Folio" + "|" + FechaEmi.substring(0, 10) + "|" + FechaEmi.substring(11, 19) + "|" + SubTotal
						+ "|" + Descuento + "|" + TotalFact + "|" + TipoComprobante + "|" + RfcEmisor + "|" + NombreEmi
						+ "|" + RfcReceptor + "|" + NumDiasPagados + "|" + FechaPago + "|" + FechaInicioRelLaboral + "|"
						+ PeriodicidadPago + "|" + UbicacionEmpleado;
				System.out.println("Folio|" + FechaEmi.substring(0, 10) + "|" + FechaEmi.substring(11, 19) + "|"
						+ SubTotal + "|" + Descuento + "|" + TotalFact + "|" + TipoComprobante + "|" + RfcEmisor + "|"
						+ NombreEmi + "|" + RfcReceptor + "|" + NumDiasPagados + "|" + FechaPago + "|"
						+ FechaInicioRelLaboral + "|" + PeriodicidadPago + "|" + UbicacionEmpleado);
				long time = System.currentTimeMillis();
				java.sql.Date date = new java.sql.Date(time);
				SimpleDateFormat formateador = new SimpleDateFormat("yyMMdd");
				String fh_odateA = formateador.format(date);
				File ficherofactor = new File("/home/jemed/OpenMarket/OPEN_FRAUDES_" + fh_odateA + ".TXT");
				PrintWriter filefactor = new PrintWriter(ficherofactor);
				WriteFile(filefactor, r);
				filefactor.flush();

			}

			if (RfcEmisor == "" || RfcReceptor == "" || UUID == "" || TotalFact == "") {
				STATUS.value = "1";
				RESPUESTA.value = "ERROR: LA ESTRUCTURA DEL XML ES INCORRECTA.";
				Xml.value = null;
				Pdf.value = null;
				InfoSat = null;
			}
			try {
				// System.out.println("XML >>>>>>>>>>>>>>>>"+GetXML);
				Connection connectionBD = null;
				InvocaValidatorCFD33 validador;
				try {
					System.out.println("subtring >>>>>>>>>>>>>>>>" + GetXML);
					String xml = "";
					validador = new InvocaValidatorCFD33(CONFIG_VALIDADOR, connectionBD);
					if (GetXML.contains("?<?")) {
						xml = GetXML.substring(1, GetXML.length());
					} else {
						xml = GetXML;
					}
					validXml = validador.validaLinea(xml);
					System.out.println("validXml >>>>>>>>>>>>>>>>" + validXml);
					Regreso = validXml.toString();
					System.out.println("Regreso validXml >>>>>>>>>>>>>>>>" + Regreso);
					strValida = validXml.toString();
					if (validXml.toString().trim().equals("")) {
						isXml = true;
					} else {
						isXml = false;
					}
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (TransformerConfigurationException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			} catch (Exception e) {
				STATUS.value = "1";
				RESPUESTA.value = "ERROR: LA ESTRUCTURA DEL XML ES INCORRECTA.";
				Xml.value = null;
				Pdf.value = null;
				InfoSat = null;
				e.printStackTrace();
			}

		}
		GetXML getXML = new GetXML();
		CallQuery CALLQ = getXML.loadProperties(PATHPROPERTIES);// /llamada
																// properties
		System.out.println("array >>>>>>>>>>>>>>>>" + array + " isXML >>>>>>>>>>>>>" + isXml);

		if (array || isXml) {
			System.out.println(
					"validacionSat: datos a validar: " + RfcEmisor + "|" + RfcReceptor + "|" + UUID + "|" + TotalFact);
			Regreso = Validaciones.ValidoInformacion(RfcEmisor, RfcReceptor, UUID, TotalFact);
			strValida = Validaciones.generateStrEnvio(RfcEmisor, RfcReceptor, TotalFact, UUID);
			System.out.println("XML >>>>>>>>>>" + isXml);
		}

		else if (Pdf != null && Pdf.value != null && Pdf.value.length != 0) {
			System.out.println("validacionSat: validaPDF");
			FileOutputStream fos;
			String nameFile = "validacionSat" + System.currentTimeMillis() + ".pdf";
			File filePdf = new File(CALLQ.getPath_pdf_sat() + nameFile);
			try {
				fos = new FileOutputStream(filePdf);
				fos.write(Pdf.value);
				fos.close();

				strValida = ReadQR.readQRfromPDF(filePdf);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				STATUS.value = "1";
				RESPUESTA.value = "PDF INVALIDO";
			} catch (IOException e) {
				e.printStackTrace();
				STATUS.value = "1";
				RESPUESTA.value = "PDF INVALIDO";
			}
			if (filePdf.exists()) {
				filePdf.delete();
			}
			Regreso = Validaciones.validaQR(strValida);

		}

		STATUS.value = "";
		RESPUESTA.value = "";
		String Salida = "";
		try {
			if (Regreso.isEmpty() && strValida != null && !strValida.isEmpty()) {
				try {
					System.out.println("Regreso >>>>><<<<" + Regreso);
					if (CALLQ.getValidaSAT().equals("true")) {
						System.out.println("Incia cmd  >>>>>>>>>>>>>>>>");
						String cmd = CALLQ.getCmd_sat() + " " + strValida.replace("&", "#") + " "
								+ CALLQ.getVAL_IpProxy() + " " + CALLQ.getVAL_PuertoProxy();
						System.out.println(cmd);
						Salida = Utilerias.ejecutarSSHLocal(cmd);
						System.out.println("salida" + Salida);
					} else {
						System.out.println("Inicia soap valida SAT");
						SocketAddress socketAddress = new InetSocketAddress(ParametrosProxy.PROXY,
								Integer.parseInt(ParametrosProxy.PORT));
						proxyConn = new Proxy(Proxy.Type.HTTP, socketAddress);
						url = new URL("https://consultaqr.facturaelectronica.sat.gob.mx/ConsultaCFDIService.svc");
						connection = (HttpsURLConnection) url.openConnection(proxyConn);
						if (!(connection instanceof HttpsURLConnection)) {
							throw new HTTPException(500);// Exception("URL is not an HTTPS URL");
						} else {
							HttpsURLConnection httpsURLConnection = (HttpsURLConnection) connection;
							// httpsURLConnection.setSSLSocketFactory(new TLSSocketConnectionFactory());

						}

						System.out.println("INI >>>>>>>>>>>>>>>>String reqXM");
						String reqXML = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org/\" xmlns:se=\"http://schemas.datacontract.org/2004/07/Sat.Cfdi.Negocio.ConsultaCfdi.Servicio\">\n"
								+ "<soapenv:Header/>\n" + "<soapenv:Body>\n" + "<tem:Consulta>\n" + "<!--Optional:-->\n"
								+ "<tem:expresionImpresa><![CDATA[?re=" + RfcEmisor + "&rr=" + RfcReceptor + "&tt="
								+ TotalFact + "&id=" + UUID + "]]>\n" + "<!--Optional:-->\n" +

								"</tem:expresionImpresa>\n" + "</tem:Consulta>\n" + "</soapenv:Body>\n"
								+ "</soapenv:Envelope>";

						System.out.println("INI >>>>>>>>>>>>>>>>String reqXM" + reqXML);
						ByteArrayOutputStream bout = new ByteArrayOutputStream();

						byte[] buffer = new byte[reqXML.length()];
						buffer = reqXML.getBytes();
						bout.write(buffer);
						byte[] b = bout.toByteArray();
						System.out.println("Inicia SOAP http://tempuri.org/IConsultaCFDIService/Consulta");
						String SOAPAction = "http://tempuri.org/IConsultaCFDIService/Consulta"; // getProperty("URL_SAOPACTION").trim();

						connection.setRequestProperty("Content-type", "text/xml; charset=UTF-8");

						connection.setRequestProperty("Content-Length", String.valueOf(b.length));

						connection.setRequestProperty("SOAPAction", SOAPAction);

						connection.setRequestMethod("POST");
						connection.setDoOutput(true);
						connection.setDoInput(true);
						System.out.println("SOAPAction complete");

						OutputStream out = connection.getOutputStream();
						out.write(b);

						out.close();
						System.out.println("OutputStream");
						isr = new InputStreamReader(connection.getInputStream());

						in = new BufferedReader(isr);
						System.out.println("InputStreamReader  >>>>>>>>");
						while ((responseString = in.readLine()) != null) {
							Salida = outputString + responseString;
						}
					}

				} catch (Exception e) {
					Salida = "3";
					e.printStackTrace();
				}

				if (!Salida.isEmpty() && (Salida.substring(0, 1).equals("0") || Salida.substring(0, 1).equals("1"))) {
					String[] Respuestas = Salida.split("\\|");
					STATUS.value = Respuestas[0];
					RESPUESTA.value = Respuestas[1];
				} else { // if (Salida.contains("3")
					STATUS.value = "1";
					RESPUESTA.value = "ERROR EN LA COMUNICACION CON EL WS DEL SAT";
					if (isXml && CALLQ.getFlag_xml().equals("true")) {
						System.out.println("No pude entrar al WS del SAT , se valida XML por parte de MEDC");
						String[] Respuestas = null;
						Respuestas = Validaciones.validomas(GetXML, CALLQ).split("\\|");
						if (Respuestas[0].trim().equals("0")) {
							String ReferenciaGenerada = "";
							if (CALLQ.getVal_EsProduccion().trim().equals("true")) {
								ReferenciaGenerada = AddInformacion(Xml, Pdf, RfcEmisor, RfcReceptor, UUID, CALLQ);
							} else {
								ReferenciaGenerada = "VALIDACION EXITOSA";
							}

							if (ReferenciaGenerada.contains("ERROR")) {
								STATUS.value = "1";
								RESPUESTA.value = "ERROR: 5004";

							} else {
								STATUS.value = "0";
								RESPUESTA.value = ReferenciaGenerada;

							}
						} else {
							STATUS.value = Respuestas[0];
							RESPUESTA.value = Respuestas[1];
						}
					}
				}

			} else if (Regreso.isEmpty() && (strValida == null || strValida.isEmpty())) {
				STATUS.value = "1";
				RESPUESTA.value = "NO SE ENCONTRARON DATOS PARA VALIDAR";
			} else if (!isXml) {
				STATUS.value = "1";
				RESPUESTA.value = validXml.toString();
			}

			else {
				String[] Respuestas = Regreso.split("\\|");
				STATUS.value = Respuestas[0];
				RESPUESTA.value = Respuestas[1];

			}
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SignatureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExceptionValidacion e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Pdf.value = null;
		Xml.value = null;
	}
	
	
	public static void WriteFile(PrintWriter printWriter, String string) {
		printWriter.println(string);
	}
	private String setArgumentos(String serie, String folio, String fEmision,
			String CUAE, String opcion) {
		folio = folio.trim();
		fEmision = fEmision.trim();
		CUAE = CUAE.trim();
		opcion = opcion.trim();
		String chkField = "";
		chkField = serie.trim().equals("") == Boolean.TRUE.booleanValue() ? "La serie de la factura es un campo obligatorio."
				: "";
		chkField = folio.trim().equals("") == Boolean.TRUE.booleanValue() ? "El folio de la factura es un campo obligatorio."
				: "";
		chkField = fEmision.trim().equals("") == Boolean.TRUE.booleanValue() ? "La fecha de emisiÃ³n de la factura es un campo obligatorio."
				: "";
		chkField = CUAE.trim().equals("") == Boolean.TRUE.booleanValue() ? "La Clave de Acceso (CUAE) es un campo obligatorio."
				: "";
		chkField = opcion.trim().equals("") == Boolean.TRUE.booleanValue() ? "El origen de de la factura es un campo obligatorio."
				: "";
		return chkField;
	}

	private String traigoopcion(String Opcion) {
		if (Opcion.trim().equals("0")) {
			Opcion = "FACATM";
		} else if (Opcion.trim().equals("1"))
			Opcion = "FACTFI";
		return Opcion;
	}

	private String setArgumentosCFDI(String serie, String folio,
			String fEmision, String CUAE, String opcion) {

		folio = folio.trim();
		fEmision = fEmision.trim();
		CUAE = CUAE.trim();
		opcion = opcion.trim();

		String chkField = "";
		chkField = serie.trim().equals("") == Boolean.TRUE.booleanValue() ? "El RFC es un campo obligatorio."
				: "";
		chkField = folio.trim().equals("") == Boolean.TRUE.booleanValue() ? "El UUID es un campo obligatorio."
				: "";
		chkField = fEmision.trim().equals("") == Boolean.TRUE.booleanValue() ? "La fecha de emisiÃ³n de la factura es un campo obligatorio."
				: "";
		chkField = CUAE.trim().equals("") == Boolean.TRUE.booleanValue() ? "La Clave de Acceso (CUAE) es un campo obligatorio."
				: "";
		chkField = opcion.trim().equals("") == Boolean.TRUE.booleanValue() ? "El origen de de la factura es un campo obligatorio."
				: "";
		return chkField;
	}

	private String validaDatos(String serie, String folio, String fEmision,
			String CUAE, String opcion, Boolean CFDI) {
		validaInformacion validador = new validaInformacion();
		return validador.validaFormatoCampos(serie, folio, fEmision, opcion,
				CFDI);
	}

	private String validaDatosCFDI(String serie, String folio, String fEmision,
			String CUAE, String opcion, Boolean CFDI) {
		validaInformacion validador = new validaInformacion();
		return validador.validaFormatoCampos(serie, folio, fEmision, opcion,
				CFDI);
	}

	private String creaCUAE(String serie, String folio, String fEmision,
			String valorPrivado, Boolean CFDI) {
		String CUAEGenerada = "";
		if (!CFDI.booleanValue()) {
			SF01 seguridad = new SF01();
			CUAEGenerada = seguridad.encode(serie.trim(), folio.trim(),
					fEmision.trim(), valorPrivado.trim());
			System.out.println("CUAEGenerada_1: " + CUAEGenerada);
		} else {
			SF01 seguridad = new SF01();
			System.out.println("serie: " + serie);
			System.out.println("folio: " + folio);
			System.out.println("fEmision: " + fEmision);
			System.out.println("valorPrivado: " + valorPrivado);
			CUAEGenerada = seguridad.encode(serie.trim(), folio.trim(),
					fEmision.trim(), valorPrivado.trim());
			System.out.println("CUAEGenerada_2: " + CUAEGenerada);
		}
		return CUAEGenerada;
	}

	private String validateCUEA(String serie, String folio, String fEmision,
			String valorPrivado, String CUAE, Boolean CFDI) {
		String CUAEGenerada = creaCUAE(serie, folio, fEmision, valorPrivado,
				CFDI);
		if (CUAE.equals(CUAEGenerada)) {
			return "";
		}
		return "La CUAE recibida no es valida.";
	}

	private String[] getCd_XML(String serie, String folio, String fEmision,
			String opcion, Boolean CFDI, CallQuery CALLQ, GetXML getXML) {

		return getXML.GetCD_XML(opcion, serie, folio, fEmision, CFDI, CALLQ);
	}

	private String getExisteXML(String serie, String folio, String fEmision,
			String opcion, Boolean CFDI, CallQuery CALLQ, GetXML getXML,
			Connection con) {

		return getXML.GetExisteXML(opcion, serie, folio, fEmision, CFDI, CALLQ,
				con);
	}

	private String RFCamp(String Rfc) {

		if (Rfc.contains("&amp")) {
			Rfc = Rfc.replace("&amp;", "&");

		}

		return Rfc;
	}

	private String VerificoExist(String Referencia, CallQuery Call) {
		String Regreso = "";
		ConsultaSAT get = new ConsultaSAT();
		System.out.println("Voy a entrar a buscar si existe");
		Regreso = get.GetExistencia(Referencia, Call);
		System.out.println("Regreso: " + Regreso);
		return Regreso;

	}

	private String[] TraigoClobs(String Referencia, CallQuery Call) {

		ConsultaSAT get = new ConsultaSAT();
		return get.Traigoclobs(Referencia, Call);

	}

	private List TraigoDatos(String Xml) {
		String RfcEmisor = null, RfcReceptor = null, UUID = null, TotalFact = null ,
			   FechaEmision="", SubTotal="", Descuento="", TipoComprobante="", 
			   NumDiasPagados="", NombreEmi="", FechaPago="", FechaInicioRelLaboral="", 
			   PeriodicidadPago="", UbicacionEmpleado="";
		List<String> Datos = new ArrayList<String>();
		try {
			CFDI cfdi = new CFDI(Xml.toString());
			RfcEmisor = cfdi.getRfcEmisor();
			RfcReceptor = cfdi.getRfcReceptor();
			TotalFact = cfdi.getTotal();
			UUID = cfdi.getsUUID();
			FechaEmision=cfdi.getFechaEmision();
			SubTotal=cfdi.getSubtotal();
			Descuento=cfdi.getDescuento();
			TipoComprobante=cfdi.getTipoComprobante();
			NombreEmi=cfdi.getNombreEmi();
			NumDiasPagados=cfdi.getNumDiasPagados();
			FechaPago=cfdi.getFechaPago();
			FechaInicioRelLaboral=cfdi.getFechaInicioRelLaboral();
			PeriodicidadPago=cfdi.getPeriodicidadPago();
			UbicacionEmpleado=cfdi.getUbicacionEmpleado();
			Datos.add(RfcEmisor);
			Datos.add(RfcReceptor);
			Datos.add(TotalFact);
			Datos.add(UUID);
			Datos.add(FechaEmision);
			Datos.add(SubTotal);
			Datos.add(Descuento);
			Datos.add(TipoComprobante);
			Datos.add(NombreEmi);
			Datos.add(NumDiasPagados);
			Datos.add(FechaPago);
			Datos.add(FechaInicioRelLaboral);
			Datos.add(PeriodicidadPago);
			Datos.add(UbicacionEmpleado);
			

		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExceptionValidacion e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Datos;
	}

	private String TraigoXml(ByteArrayHolder xml) {
		String roundTrip = "";
		try {
			roundTrip = new String(xml.value, "UTF8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return roundTrip;
	}

	private String AddInformacion(ByteArrayHolder XMl, ByteArrayHolder Pdf,
			String RfcEmisor, String RfcReceptor, String UUID, CallQuery Call)
			throws SQLException {

		String Regreso = "";
		ConsultaSAT set = new ConsultaSAT();
		Regreso = set.SetInfo(XMl, Pdf, RfcEmisor, RfcReceptor, UUID, Call);
		return Regreso;

	}

	private boolean validaFecha(String fechaAct, StringHolder EXIT_ESTATUS) {
		boolean fechaValida = false;
		if (fechaAct != null && !fechaAct.isEmpty()) {
			String[] fecha = fechaAct.split("/");
			if (fecha != null && fecha.length == 3) {
				if (fecha[0] != null && !fecha[0].isEmpty()) {
					try {
						Integer dia = new Integer(fecha[0].trim());
						if (fecha[1] != null && !fecha[1].isEmpty()) {
							try {
								Integer mes = new Integer(fecha[1].trim());
								if (mes > 0 && mes < 13) {
									try {
										Integer anio = new Integer(
												fecha[2].trim());
										fechaValida = true;
									} catch (Exception ex) {
										EXIT_ESTATUS.value = "AÃ±o invalido, los argumentos de fecha deben de estar en el formato dd/mm/yyyy.";
									}
								}
							} catch (Exception ex) {
								EXIT_ESTATUS.value = "Mes invalido, los argumentos de fecha deben de estar en el formato dd/mm/yyyy.";
							}
						}
					} catch (Exception ex) {
						EXIT_ESTATUS.value = "Dia invalido, los argumentos de fecha deben de estar en el formato dd/mm/yyyy.";
					}
				}
			} else {
				EXIT_ESTATUS.value = "Los argumentos de fecha deben de estar en el formato dd/mm/yyyy.";
			}
		} else {
			EXIT_ESTATUS.value = "Se requiere que los argumentos de fecha esten en formato dd/mm/yyyy.";
		}
		return fechaValida;
	}

	private boolean verificaReferencias(String referencias) {
		boolean validas = false;
		if (referencias.contains(",")) {
			validas = true;
		}
		return validas;
	}

	private List<Referencia> getRefereencias(String referencias) {
		List<Referencia> lReferencias = new ArrayList<Referencia>();
		String[] refSep = referencias.split("\\|");
		if (refSep != null && refSep.length > 0) {
			for (int i = 0; i < refSep.length; i++) {
				String[] refSepDatos = refSep[i].split(",");
				if (refSepDatos != null && refSepDatos.length > 0) {
					if (refSepDatos[0] != null && refSepDatos[1] != null) {
						lReferencias.add(new Referencia(refSepDatos[0].trim(),
								refSepDatos[1].trim()));
					}
				}
			}
		}
		return lReferencias;
	}

	private String getTXIdenComp(ArrayList<String> listatxIde) {
		String txIden = "";
		String sep = "";
		if (listatxIde != null && listatxIde.size() > 0) {
			int posIdent = 0;
			for (String sTXIden : listatxIde) {
				posIdent = sTXIden.lastIndexOf("_");
				txIden += sep + "instr(r.tx_identificador, '"
						+ sTXIden.substring(0, posIdent) + "') > 0";
				sep = " OR ";
			}
			System.out.println("txIden...................: " + txIden);
		}
		return txIden;
	}

	private List<String> setPeriodo(List<String> periodos, String periodo) {
		boolean agregaPer = true;
		for (String per : periodos) {
			if (per.equals(periodo)) {
				agregaPer = false;
				break;
			}
		}
		if (agregaPer) {
			periodos.add(periodo);
		}
		return periodos;
	}

	private HashMap<String, String> getRefereenciasCorreo(String referencias) {
		HashMap<String, String> lReferencias = new HashMap<String, String>();
		List<String> periodos = new ArrayList<String>();
		String sReferencias = "";
		String sPeriodos = "";
		String sep = "";
		String[] refSep = referencias.split("\\|");
		if (refSep != null && refSep.length > 0) {
			for (int i = 0; i < refSep.length; i++) {
				String[] refSepDatos = refSep[i].split(",");
				if (refSepDatos != null && refSepDatos.length > 0) {
					if (refSepDatos[0] != null && refSepDatos[1] != null) {
						setPeriodo(periodos, refSepDatos[1]);
						sReferencias += sep + "'" + refSepDatos[0] + "'";
						sep = ",";
					}
				}
			}
			System.out.println("sReferencias................: " + sReferencias);
			sep = "";
			if (periodos.size() > 0) {
				for (String per : periodos) {
					sPeriodos += sep + "'" + per + "'";
					sep = ",";
				}
			}
			System.out.println("sPeriodos...................: " + sPeriodos);
			lReferencias.put("referencias", sReferencias);
			lReferencias.put("periodos", sPeriodos);
		}
		return lReferencias;
	}

	private boolean validaRangoFechas(String fechaInicial, String fechaFinal,
			StringHolder EXIT_ESTATUS) {
		boolean valido = false;
		Calendar calIni = Calendar.getInstance();
		Calendar calFin = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		try {
			Date dateIni = formatter.parse(fechaInicial);
			Date dateFin = formatter.parse(fechaFinal);
			if (dateIni != null && dateFin != null) {
				if (dateIni.before(dateFin)) {
					calIni.setTime(dateIni);
					calFin.setTime(dateFin);
					Long res = Utilerias.diferenciaHorasDias(calIni, calFin);
					if (res != null) {
						int resp = (res.intValue() / 24);
						if (resp < 16) {
							valido = true;
						} else {
							EXIT_ESTATUS.value = "El rango de fechas no debe de pasar de 15 dias.";
						}
					} else {
						EXIT_ESTATUS.value = "El rango de fechas no debe de pasar de 15 dias.";
					}
				} else {
					EXIT_ESTATUS.value = "La fecha inicial tiene que ser menor a la fecha final.";
				}
			}

		} catch (ParseException e) {
			e.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return valido;
	}

	public String getConstanciasCliente(String rfcReceptor,
			StringHolder EXIT_ESTATUS) {
		String constancias = "";
		GetXML getXML = new GetXML();
		try {
			// if(rfcEmisor != null && !rfcEmisor.isEmpty()){
			// if(rfcReceptor != null && !rfcReceptor.isEmpty()){
			System.out.println("rfcReceptor: " + rfcReceptor);
			CallQuery CALLQ = getXML.loadProperties(PATHPROPERTIES);
			constancias = getXML.getTiposConstancia(rfcReceptor, CALLQ)
					.toString();
			if (constancias != null && !constancias.isEmpty()) {
				EXIT_ESTATUS.value = "OK";
				System.out.println("OK");
			} else {
				System.out.println("No se encontraron tipos de constancia");
				EXIT_ESTATUS.value = "No se encontraron tipos de constancia";
			}
			/*
			 * }else{ EXIT_ESTATUS.value =
			 * "El rfcReceptor no puede ser nulo o vacio"; }
			 */
			/*
			 * }else{ EXIT_ESTATUS.value =
			 * "El rfcEmisor no puede ser nulo o vacio"; }
			 */
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			EXIT_ESTATUS.value = "No se pudieron cargar los tipos de constancias.";
			ex.printStackTrace();
		}
		return constancias;
	}

	public byte[] getPDFGMC(String param1, String param2, String param3,
			String param4, String param5, StringHolder EXIT_ESTATUS) {
		String[] args = new String[6];
		String pathDwnldFile = "";
		String pathServer = "";
		String separador = "";
		String pathGMC = "";
		byte[] resp = null;
		if (param1 != null && !param1.isEmpty()) {
			args[0] = param1;
			if (param2 != null && !param2.isEmpty()) {
				args[1] = param2;
				if (param3 != null && !param3.isEmpty()) {
					args[2] = param3;
					if (param4 != null && !param4.isEmpty()) {
						args[3] = param4;
						if (param5 != null && !param5.isEmpty()) {
							args[4] = param5;
							try {
								Properties pr = new Properties();
								Properties sg = new Properties();

								try {

									pr.load(new FileInputStream(PATHPROPERTIES));
									sg.load(new FileInputStream(pr
											.getProperty("PATH_GMC")));
									separador = "/";

								} catch (Exception e) {

									pr.load(new FileInputStream(
											"C:\\Pruebas\\querysFacturasCFDI.properties")); // PRUEBAS
									sg.load(new FileInputStream(pr
											.getProperty("PATH_GMC")));
									separador = "\\";

								}
								pathGMC = pr.getProperty("PATH_GMC");
								pathDwnldFile = sg
										.getProperty("PATH_LOCAL_OUT_W6");
								pathServer = sg.getProperty("URL_SERVER");
								GetXML getXML = new GetXML();
								CallQuery CALLQ = getXML
										.loadProperties(PATHPROPERTIES);
								String directorioRaiz = CALLQ.getPathPDF();
								String nombreJar = CALLQ.getJARGMC();
								System.out.println("param1: " + args[0]);
								System.out.println("param2: " + args[1]);
								System.out.println("param3: " + args[2]);
								System.out.println("param4: " + args[3]);
								System.out.println("param5: " + args[4]);

								System.out.println("Ejecutando CallFtp: "
										+ args[0] + " " + args[1] + " "
										+ args[2] + " " + args[3] + " "
										+ args[4] + " :" + args[5] + ":");

								// HashMap mapRespuesta = CallFtp.ejecuta(args);

								// ==========================================

								Connection conEDC = null;
								Connection conFAC = null;

								Properties prop = new Properties();
								prop.load(new FileInputStream(args[0]));

								conEDC = dao.ConnectionBD.getConnectionBD(prop
										.getProperty("PATH_BD"));
								conFAC = dao.ConnectionBD.getConnectionBD(prop
										.getProperty("PATH_BD_FAC"));

								Ejecuta pdfeje = new Ejecuta();

								HashMap mapRespuesta = pdfeje.ejecuta(args,
										conEDC, conFAC);
								conEDC.close();
								conFAC.close();

								// ==================================================

								String respuesta = "";
								String nomArchivo = "";
								String codigoRespuesta = "";
								String respError = "";
								if (mapRespuesta != null) {
									nomArchivo = (String) mapRespuesta
											.get("nombreArchivo");
									System.out.println("nomArchivo: "
											+ nomArchivo);
									respuesta = (String) mapRespuesta
											.get("respuesta");
									System.out.println("respuesta: "
											+ respuesta);
									codigoRespuesta = (String) mapRespuesta
											.get("codigoRespuesta");
									System.out.println("codigoRespuesta: "
											+ codigoRespuesta);
									respError = (String) mapRespuesta
											.get("errRespuesta");
									System.out.println("respError: "
											+ respError);
									if (codigoRespuesta.trim().equals("0")) {
										if (respuesta != null
												&& respuesta != "") {
											if (respuesta != "1") {
												String nomPathArchivo = pathDwnldFile
														+ nomArchivo;
												if (respuesta
														.toUpperCase()
														.contains(
																"CANNOT CREATE")) {
													EXIT_ESTATUS.value = "1";
													System.out
															.println("El archivo "
																	+ nomArchivo
																	+ " no se pudo generar.......");
												} else {
													System.out
															.println("Respuesta Archivo: "
																	+ nomPathArchivo);
													File archivo = new File(
															nomPathArchivo);
													archivo.canExecute();
													archivo.canRead();
													archivo.canWrite();
													if (archivo.exists()
															|| archivo.length() > 0L) {
														String pathDoc = "";
														pathDoc = pathServer
																+ nomArchivo;
														resp = IOUtils
																.toByteArray(new FileInputStream(
																		archivo));
														EXIT_ESTATUS.value = "0";
													} else {
														EXIT_ESTATUS.value = "El archivo no existe o el archivo se genero vacio.";
													}
												}
											} else {
												EXIT_ESTATUS.value = "No se encontro documento con esos criterios.";
											}
										} else {
											EXIT_ESTATUS.value = "Error al generar el documento respuesta vacia.";
										}
									} else {
										EXIT_ESTATUS.value = codigoRespuesta;
									}
								} else {
									EXIT_ESTATUS.value = "Error al generar el documento respuesta nula.";
								}
							} catch (Exception ex) {
								ex.printStackTrace();
								EXIT_ESTATUS.value = "Error";
							}
						} else {
							EXIT_ESTATUS.value = "Se requiere el tipo de nombre a generar; 0 normal o 1 referencia.";
						}
					} else {
						EXIT_ESTATUS.value = "Se requiere el(los) Periodo(s), mas de uno, debe ir separado por comas.";
					}
				} else {
					EXIT_ESTATUS.value = "Se requiere el tipo de filtro; 0 referencia o 1 cuenta";
				}
			} else {
				EXIT_ESTATUS.value = "Se requiere la(s) referecia/cuenta(s), separadas por comas.";
			}
		} else {
			EXIT_ESTATUS.value = "Se requiere el Path del archivo de configuracion.";
		}
		return resp;
	}

	private String getFileExtension(File file) {
		String name = file.getName();
		try {
			return name.substring(name.lastIndexOf(".") + 1);
		} catch (Exception e) {
			return "";
		}
	}

	private byte[] readContentIntoByteArray(File file) throws Exception {
		FileInputStream fileInputStream = null;
		byte[] bFile = new byte[(int) file.length()];
		fileInputStream = new FileInputStream(file);
		fileInputStream.read(bFile);
		fileInputStream.close();
		for (int i = 0; i < bFile.length; i++) {
			System.out.print((char) bFile[i]);
		}
		return bFile;
	}

	private String convertAcentosXML(String cadena) {
		if (cadena != null && !cadena.isEmpty()) {
			/*
			 * cadena = cadena.replace("Ã¡", "ÃƒÂ¡"); cadena =
			 * cadena.replace("Ã©", "ÃƒÂ©"); cadena =
			 * cadena.replace("Ã­", "ÃƒÂ­"); cadena =
			 * cadena.replace("Ã³", "ÃƒÂ³"); cadena =
			 * cadena.replace("Ãº", "ÃƒÂº"); cadena =
			 * cadena.replace("Ã±", "ÃƒÂ±"); cadena =
			 * cadena.replace("Ã�", "Ãƒ"); cadena = cadena.replace("Ã‰",
			 * "Ãƒâ€°"); cadena = cadena.replace("Ã�", "ÃƒÂ�");
			 * cadena = cadena.replace("Ã“", "Ãƒâ€œ"); cadena =
			 * cadena.replace("Ãš", "ÃƒÅ¡"); cadena =
			 * cadena.replace("Ã‘", "Ãƒâ€˜");
			 */
			cadena = cadena.replace("Ã¡", "&#225;");
			cadena = cadena.replace("Ã©", "&#233;");
			cadena = cadena.replace("Ã­", "&#237;");
			cadena = cadena.replace("Ã³", "&#243;");
			cadena = cadena.replace("Ãº", "&#250;");
			cadena = cadena.replace("Ã±", "&#241;");
			cadena = cadena.replace("Ã�", "&#193;");
			cadena = cadena.replace("Ã‰", "&#201;");
			cadena = cadena.replace("Ã�", "&#205;");
			cadena = cadena.replace("Ã“", "&#211;");
			cadena = cadena.replace("Ãš", "&#218;");
			cadena = cadena.replace("Ã‘", "&#209;");
		}
		return cadena;
	}

	public void getPDF_G22Hipotecario(Elemento[] contenido,
			StringHolder EXIT_ESTATUS, ByteArrayHolder documento,
			StringHolder URL_Consulta, StringHolder ERR_DESC) {

		try {

			Properties pr = new Properties();

			pr.load(new FileInputStream(PROPERTIES_HIPOTECARIA));

			String numContrato = contenido[0].getDatos()[0].getValor()
					.toString();

			String periodo = contenido[0].getDatos()[1].getValor().toString();

			String prefijoArchivo = pr.getProperty("prefijoArchivo");

			String repositorio = pr.getProperty("repositorio") + "-" + periodo;

			if ("true".equals(pr.getProperty("SUBSTR"))) {
				repositorio = pr.getProperty("repositorio") + "-"
						+ periodo.substring(0, 4);
			} else {
				repositorio = pr.getProperty("repositorio") + "-" + periodo;
			}

			String key = prefijoArchivo + numContrato + "_" + periodo;

			String rutaLocal = pr.getProperty("rutaLocal");
			String RIG = pr.getProperty("RIG");
			String URI_PATH_DOCUMENTS = pr.getProperty("URI_PATH_DOCUMENTS");
			String urlBase = pr.getProperty("urlBase");

			Recupera recupera = new Recupera();

			if (recupera.getXMLtoArchiving(repositorio, key, RIG,
					URI_PATH_DOCUMENTS, rutaLocal)) {
				File file = new File(rutaLocal + key + ".pdf");

				byte[] encoded = null;
				String s = "";

				encoded = Base64.encodeBase64(org.apache.commons.io.FileUtils
						.readFileToByteArray(file));

				System.out
						.println("EncodedBase64|" + new String(encoded) + "|");

				EXIT_ESTATUS.value = "0";

				URL_Consulta.value = urlBase + key + ".pdf";
				documento.value = encoded;
				ERR_DESC.value = "";
			} else {
				URL_Consulta.value = "";
				EXIT_ESTATUS.value = "1";
				ERR_DESC.value = "Error: No se pudo recuperar de Archiving";
			}

		} catch (Exception e) {
			e.printStackTrace();
			URL_Consulta.value = "";
			EXIT_ESTATUS.value = "1";
			ERR_DESC.value = "Error: " + e.toString();
		}

	}

	public void getPDF_G22(String producto, String version,
			String tipoDocumento, Elemento[] contenido,
			StringHolder EXIT_ESTATUS, ByteArrayHolder documento,
			StringHolder URL_Consulta, StringHolder ERR_DESC) {
		String pathDwnldFile = "";
		String pathServer = "";
		String separador = "";
		String pathGMC = "";
		try {

			Properties pr = new Properties();
			Properties sg = new Properties();

			try {

				pr.load(new FileInputStream(PATHPROPERTIES));
				sg.load(new FileInputStream(pr.getProperty("PATH_GMC")));
				separador = "/";

			} catch (Exception e) {

				pr.load(new FileInputStream(
						"C:\\Pruebas\\querysFacturasCFDI.properties")); // PRUEBAS
				sg.load(new FileInputStream(pr.getProperty("PATH_GMC")));
				separador = "\\";

			}
			pathGMC = pr.getProperty("PATH_GMC");
			System.out.println("ENTRO AL METODO PRINCIPAL");
			GetXML getXML = new GetXML();
			CallQuery CALLQ = getXML.loadProperties(PATHPROPERTIES);
			XML_RUC xml_ruc = new XML_RUC();
			HashMap<String, String> map = xml_ruc.objectToXML(producto,
					version, tipoDocumento, contenido, CALLQ,
					sg.getProperty("PATH_LOCAL"));

			String WFD = map.get("WFD");
			String XML = map.get("XML");
			String numcopias = map.get("numcopias");
			String tipoarchivo = "";

			boolean archstring = false;

			System.out.println("tipoDocumento: " + tipoDocumento);
			if (tipoDocumento.trim().toUpperCase().startsWith("PDF")) {
				tipoarchivo = tipoDocumento.substring(0, 3);
			} else if (tipoDocumento.trim().toUpperCase().endsWith("_S")) {
				tipoarchivo = tipoDocumento.substring(0,
						tipoDocumento.length() - 2);
				archstring = true;
			} else {
				tipoarchivo = tipoDocumento;
			}

			System.out.println("pathGMC: " + pathGMC);
			System.out.println("WFD: " + WFD);
			System.out.println("XML: " + XML);
			System.out.println("numcopias: " + numcopias);
			System.out.println("tipoarchivo: " + tipoarchivo);

			String arr[] = new String[8];
			arr[0] = pathGMC;
			arr[1] = WFD;
			arr[2] = "";
			arr[3] = "";
			arr[4] = "";
			arr[5] = XML;
			arr[6] = numcopias;
			arr[7] = tipoarchivo;

			System.out.println("pathGMC: " + pathGMC);
			System.out.println("WFD: " + WFD);
			System.out.println("XML: " + XML);
			pathDwnldFile = sg.getProperty("PATH_LOCAL_OUT_W6");
			System.out.println("pathDwnldFile: " + pathDwnldFile);
			pathServer = sg.getProperty("URL_SERVER");
			System.out.println("pathServer: " + pathServer);
			// HashMap mapRespuesta = CallFtp.ejecuta(arr);

			// ==========================================

			Connection conEDC = null;
			Connection conFAC = null;

			Properties prop = new Properties();
			prop.load(new FileInputStream(arr[0]));

			conEDC = dao.ConnectionBD.getConnectionBD(prop
					.getProperty("PATH_BD"));
			conFAC = dao.ConnectionBD.getConnectionBD(prop
					.getProperty("PATH_BD_FAC"));

			Ejecuta pdfeje = new Ejecuta();

			HashMap mapRespuesta = pdfeje.ejecuta(arr, conEDC, conFAC);
			conEDC.close();
			conFAC.close();

			// ==================================================

			String respuesta = "";
			String nomArchivo = "";
			String codigoRespuesta = "";
			String respError = "";

			if (mapRespuesta != null) {
				nomArchivo = (String) mapRespuesta.get("nombreArchivo");
				System.out.println("nomArchivo: " + nomArchivo);
				respuesta = (String) mapRespuesta.get("respuesta");
				System.out.println("respuesta: " + respuesta);
				codigoRespuesta = (String) mapRespuesta.get("codigoRespuesta");
				System.out.println("codigoRespuesta: " + codigoRespuesta);
				respError = (String) mapRespuesta.get("errRespuesta");
				System.out.println("respError: " + respError);
				if (codigoRespuesta.trim().equals("0")) {
					if (respuesta != null && respuesta != "") {
						if (respuesta != "1") {
							String nomPathArchivo = pathDwnldFile + nomArchivo;
							if (respuesta.toUpperCase().contains(
									"CANNOT CREATE")) {
								EXIT_ESTATUS.value = "1";
								URL_Consulta.value = "";
								System.out.println("El archivo " + nomArchivo
										+ " no se pudo generar.......");
							} else {
								System.out.println("Respuesta Archivo: "
										+ nomPathArchivo);
								File archivo = new File(nomPathArchivo);
								archivo.canExecute();
								archivo.canRead();
								archivo.canWrite();
								if (archivo.exists() || archivo.length() > 0L) {
									String pathDoc = "";
									pathDoc = pathServer + nomArchivo;
									if (archstring) {
										String archivostring = Utilerias
												.leerArchivo(archivo);
										URL_Consulta.value = archivostring;
										documento.value = IOUtils
												.toByteArray(archivostring);
									} else {
										URL_Consulta.value = pathDoc;
										documento.value = IOUtils
												.toByteArray(new FileInputStream(
														archivo));
									}
									EXIT_ESTATUS.value = "0";
									ERR_DESC.value = "";
								} else {
									EXIT_ESTATUS.value = "1";
									URL_Consulta.value = "";
									ERR_DESC.value = "El archivo no existe o el archivo se genero vacio.";
								}
							}
						} else {
							EXIT_ESTATUS.value = "1";
							URL_Consulta.value = "";
							ERR_DESC.value = "No se encontro documento con esos criterios.";
						}
					} else {
						EXIT_ESTATUS.value = "1";
						URL_Consulta.value = "";
						ERR_DESC.value = "Error al generar el documento respuesta vacia.";
					}
				} else {
					EXIT_ESTATUS.value = codigoRespuesta;
					URL_Consulta.value = "";
					ERR_DESC.value = respError;
				}
			} else {
				EXIT_ESTATUS.value = "1";
				URL_Consulta.value = "";
				ERR_DESC.value = "Error al generar el documento respuesta nula.";
			}
		} catch (Exception e) {
			e.printStackTrace();
			EXIT_ESTATUS.value = "1";
			URL_Consulta.value = "";
			ERR_DESC.value = "Error, no se genero el documento.";
		}
	}

	public void getPDFWithGMC(String referencia, String periodo,
			StringHolder EXIT_ESTATUS, ByteArrayHolder pdf,
			ByteArrayHolder xml, StringHolder URL_Consulta) {

		String separador = "";
		String pathDwnldFile = "";
		String pathServer = "";
		try {

			Properties pr = new Properties();
			Properties sg = new Properties();

			try {

				pr.load(new FileInputStream(PATHPROPERTIES));
				sg.load(new FileInputStream(pr.getProperty("PATH_GMC")));
				separador = "/";

			} catch (Exception e) {

				pr.load(new FileInputStream(
						"C:\\Pruebas\\querysFacturasCFDI.properties")); // PRUEBAS
				sg.load(new FileInputStream(pr.getProperty("PATH_GMC")));
				separador = "\\";

			}

			String argins[] = { pr.getProperty("PATH_GMC"), referencia, "0",
					periodo, "1", "" };
			pathDwnldFile = sg.getProperty("PATH_LOCAL_OUT_W6");
			pathServer = sg.getProperty("URL_SERVER");
			// HashMap mapRespuesta = CallFtp.ejecuta(argins);

			// ==========================================

			Connection conEDC = null;
			Connection conFAC = null;

			Properties prop = new Properties();
			prop.load(new FileInputStream(argins[0]));

			conEDC = dao.ConnectionBD.getConnectionBD(prop
					.getProperty("PATH_BD"));
			
			System.out.println(prop
					.getProperty("PATH_BD_FAC"));
			conFAC = dao.ConnectionBD.getConnectionBD(prop
					.getProperty("PATH_BD_FAC"));

			Ejecuta pdfeje = new Ejecuta();

			HashMap mapRespuesta = pdfeje.ejecuta(argins, conEDC, conFAC);
			conEDC.close();
			conFAC.close();

			// ==================================================

			String comando = "";
			String respuesta = "";
			String nomArchivo = "";
			String codigoRespuesta = "";
			String respError = "";
			if (mapRespuesta != null) {
				nomArchivo = (String) mapRespuesta.get("nombreArchivo");
				respuesta = (String) mapRespuesta.get("respuesta");
				codigoRespuesta = (String) mapRespuesta.get("codigoRespuesta");
				respError = (String) mapRespuesta.get("errRespuesta");
				if (codigoRespuesta.equals("0")) {
					if ((respuesta != null) && (respuesta != "")) {
						if (respuesta != "1") {
							String nomPathArchivo = pathDwnldFile + nomArchivo;
							if (respuesta.toUpperCase().contains(
									"CANNOT CREATE")) {
								EXIT_ESTATUS.value = "1";
								URL_Consulta.value = "";
								System.out.println("El archivo " + nomArchivo
										+ " no se pudo generar.......");
							} else {
								File archivo = new File(nomPathArchivo);
								if (archivo.exists()) {
									if (archivo.length() > 0L) {
										String pathDoc = "";
										pathDoc = pathServer + nomArchivo;
										URL_Consulta.value = pathDoc;
										pdf.value = IOUtils
												.toByteArray(new FileInputStream(
														archivo));
										EXIT_ESTATUS.value = "OK";
									} else {
										EXIT_ESTATUS.value = "El archivo se genero vacio.";
										URL_Consulta.value = "";
									}
								} else {
									EXIT_ESTATUS.value = "El archivo no existe.";
									URL_Consulta.value = "";
								}
							}
						} else {
							EXIT_ESTATUS.value = "No se encontro documento con esos criterios";
							URL_Consulta.value = "";
						}
					} else {
						EXIT_ESTATUS.value = "Error al generar el documento respuesta vacia.";
						URL_Consulta.value = "";
					}
				} else {
					EXIT_ESTATUS.value = respError;
				}
			} else {
				EXIT_ESTATUS.value = "Error al generar el documento respuesta nula.";
				URL_Consulta.value = "";
			}
		} catch (Exception e) {
			e.printStackTrace();
			EXIT_ESTATUS.value = "Error, no se genero el documento.";
		}
	}

	private String[] parseaContenido(Elemento[] elementos) {
		System.out.println("Cantidad de elemetos: " + elementos.length);
		String[] contenido = new String[elementos.length];
		String separador = "|";
		int conta = 0;
		for (Elemento elemento : elementos) {
			System.out.println("contador: " + conta);
			String sEle = elemento.getNombre() + separador;
			System.out.println("Elemento: " + sEle);
			if (elemento.getDatos() != null && elemento.getDatos().length > 0) {
				for (Dato dato : elemento.getDatos()) {
					sEle += dato.getNombreDato() + "=" + dato.getValor()
							+ separador;
				}
			} else {
				System.out.println("Datos nulos o vacios.");
			}
			System.out.println("Elemento compuesto: " + sEle);
			contenido[conta] = sEle;
			System.out.println("Elemento compuesto en arreglo: "
					+ contenido[conta]);
			conta++;
		}
		return contenido;
	}

	// VERSION 2 CONSTANCIAS

	/**
	 * @param rfcReceptor
	 * @param tipoDocumento
	 * @param EXIT_STATUS
	 * @param ERR_DESC
	 * @return
	 * 
	 *         Metodo que recibe RFC, tipo de documento para devolver un arreglo
	 *         de años.
	 * 
	 */

	public List<String> getAniosv2(String rfc, String tipoDoc,
			StringHolder EXIT_STATUS, StringHolder ERR_DESC) {
		
		System.out.println("Metodo caduco getAniosv2");
		EXIT_STATUS.value = "1";
		ERR_DESC.value = "Metodo caduco getAniosv2";
		List<String> resultado = new ArrayList<String>();
		return resultado;
	}

	/**
	 * @param IDAplic
	 * 
	 *            Metodo que recibe un ID del aplicativo para seleccionar la
	 *            plantilla a usar por el método de envío de mail.
	 * 
	 */

	public String getPlantillaMail(String ideAplic) {

		String[] plantillasLinea = null;
		GetXML getXML = new GetXML();
		CallQuery CALLQ = getXML.loadProperties(PATHPROPERTIES);
		String plantillaN = "";
		String plantillaMail = "";

		plantillaN = CALLQ.getPlantillaMailID();

		plantillasLinea = plantillaN.split("\\,");

		try {
			for (int x = 0; x < plantillasLinea.length; x++) {
				if (plantillasLinea[x].startsWith(ideAplic)) {
					plantillaMail = plantillasLinea[x];
					plantillaMail = plantillaMail.replace("|", "");
					plantillaMail = plantillaMail.replace(ideAplic, "");
					// System.out.println("Nombre de la plantilla: " +
					// plantillaMail);
				}
			}
		} catch (Exception e) {
			System.out.println("Error al intentar obtener la plantillaMail");
		}
		return plantillaMail;

	}

	/**
	 * @param infoDocumentos
	 * @param email
	 * @param contrasenia
	 * @param EXIT_STATUS
	 * @param ERR_DESC
	 * 
	 *            Metodo que recibe arreglo de informacion y realiza el envio de
	 *            correo En el metodo getDocuConstancias genera los PDF, zipe y
	 *            envia
	 * 
	 */
	public void enviarCorreoDocumentosv2(InfoDocumentosv2 infoDocumentosv2,
			String email, String contrasenia, String ideAplic,
			StringHolder EXIT_STATUS, StringHolder ERR_DESC) {
		
		System.out.println("Metodo caduco enviarCorreoDocumentosv2");
		EXIT_STATUS.value = "1";
		ERR_DESC.value = "Utilizar metodo enviarCorreoDocumentos";
		
	}

	/**
	 * @param email
	 * @param contrasenia
	 * @param periodo
	 * 
	 *            Llena variables para poder procesar ya sea el XML o PDF En el
	 *            metodo procesaArchivosConstancias genera los PDF´s XML´s y
	 *            envia por correo dependiendo de las opciones
	 * 
	 * 
	 */
	private byte[] getDocuConstanciasv2(String email, String contrasenia,
			String tipoArchivo, String referencias, String plantillaMail,
			StringHolder EXIT_ESTATUS, StringHolder ERR_DESC,
			ArrayList<String> listatxIde, String periodo) {
		byte[] document = null;
		
			EXIT_ESTATUS.value = "1";
			ERR_DESC.value = "Metodo caduco getDocuConstanciasv2 ";
		
		return document;
	}

	/**
	 * @param archivos
	 * @param ejecutaPDF
	 * @param ejecutaXML
	 * @param email
	 * @param contrasenia
	 * @param periodo
	 * @param tipoArchivo
	 * @param EXIT_ESTATUS
	 * @return
	 * @throws Exception
	 * 
	 *             Genera PDF´s, XML´s y concatena o envia dependiendo sea el
	 *             caso
	 */
	private byte[] procesaArchivosConstanciasv2(List<Archivo> archivos,
			String genPDFXML, String email, String plantillaMail,
			String contrasenia, String periodo, String tipoArchivo,
			StringHolder EXIT_ESTATUS, StringHolder ERR_DESC,
			ArrayList<String> listaRefNoAden, String referencias) {
		byte[] respuesta = null;
		
		EXIT_ESTATUS.value = "1";
		ERR_DESC.value = "El servicio procesaArchivosConstanciasv2 no esta disponible";
		
		return respuesta;
	}

	/**
	 * @param referencias
	 * @return referenciasRef
	 * @throws Exception
	 * 
	 *             Obtiene las referencias en forma lineal para el proceso de
	 *             generacion de PDF con GMC
	 */
	private String obtenerReferenciasRef(String referencias) {

		String referenciasRef = "";
		String[] referenciasLinea = null;
		StringBuffer sb = new StringBuffer();
		referenciasLinea = referencias.split("\\|");
		int sizeList = (referenciasLinea.length) - 1;
		try {
			for (int x = 0; x < referenciasLinea.length; x++) {
				// int fin = referenciasLinea[x].length();
				int ini = referenciasLinea[x].lastIndexOf(",");

				if (x < sizeList) {
					sb.append(referenciasLinea[x].substring(0, ini).concat(","));
				} else if (x == sizeList) {
					sb.append(referenciasLinea[x].substring(0, ini));
				}

			}
			System.out.println("Referencias: " + sb.toString());
			referenciasRef = sb.toString();
		} catch (Exception e) {

		}
		return referenciasRef;

	}

	/**
	 * @param referencias
	 * @return periodos
	 * @throws Exception
	 * 
	 *             Obtiene los periodos en forma lineal para el proceso de
	 *             generacion de PDF con GMC
	 */
	private String obtenerPeriodosRef(String referencias) {
		String periodos = "";
		String[] referenciasLinea = null;
		StringBuffer sb = new StringBuffer();
		referenciasLinea = referencias.split("\\|");
		int sizeList = (referenciasLinea.length) - 1;

		for (int x = 0; x < referenciasLinea.length; x++) {
			int fin = referenciasLinea[x].length();
			int ini = referenciasLinea[x].lastIndexOf(",") + 1;

			if (x < sizeList) {
				sb.append(referenciasLinea[x].substring(ini, fin).concat(","));
			} else if (x == sizeList) {
				sb.append(referenciasLinea[x].substring(ini, fin));
			}

		}
		System.out.println("Periodos: " + sb.toString());
		periodos = sb.toString();
		return periodos;

	}

	/**
	 * @param referencias
	 * @param periodos
	 * @param xmls
	 * @return OK
	 * @throws Exception
	 * 
	 *             Genera las constancias en formato .pdf con GMC.
	 */
	private void generaPdfGMC(String referencias, String periodos, String xmls) {
		GetXML getXml = new GetXML();
		CallQuery CALLQ = getXml.loadProperties(PATHPROPERTIES);
		Properties sg = new Properties();
		String pathGMC = "";
		try {
			sg.load(new FileInputStream(CALLQ.getPathGetGMC()));
			pathGMC = CALLQ.getPathGetGMC();

			String[] f = { pathGMC, referencias, "0", periodos, "1", "" };
			// @SuppressWarnings("rawtypes")
			// HashMap mapRespuesta = CallFtp.ejecuta(f);

			// ==========================================

			Connection conEDC = null;
			Connection conFAC = null;

			Properties prop = new Properties();
			prop.load(new FileInputStream(f[0]));

			conEDC = dao.ConnectionBD.getConnectionBD(prop
					.getProperty("PATH_BD"));
			conFAC = dao.ConnectionBD.getConnectionBD(prop
					.getProperty("PATH_BD_FAC"));

			Ejecuta pdfeje = new Ejecuta();

			HashMap mapRespuesta = pdfeje.ejecuta(f, conEDC, conFAC);
			conEDC.close();
			conFAC.close();

			// ==================================================

			String verifica = (String) mapRespuesta.get("codigoRespuesta");
			String nombreFile = (String) mapRespuesta.get("nombreArchivo");
			String nombreFileConca = sg.getProperty("PATH_LOCAL_OUT_W6")
					.concat(nombreFile);
			System.out.println("Nombre del archivo" + nombreFileConca);
			System.out.println("IMPRIME VERIFICA: " + verifica);
		} catch (Exception e) {

		}
	}

	/**
	 * @param referencias
	 * @param periodos
	 * @param xmls
	 * @return OK
	 * @throws Exception
	 * 
	 *             Genera las constancias en formato .pdf con Adobe.
	 */
	private void generaPdfAdobe(String avFilial, String filial,
			Archivo archivo, String nombrePDF, ByteArrayHolder pdf,
			FileOutputStream archPDF, ListaDocumentos listDocs, String xmls,
			StringHolder EXIT_ESTATUS, StringHolder ERR_DESC) {

		GetXML getXml = new GetXML();
		String rutaArchivoPDF = "";
		String rutaCarpeta = "";
		List<InputStream> rutasPdf = new ArrayList<InputStream>();
		SF01 oSF01 = new SF01();

		try {

			if (avFilial.equals("CON")) {
				BLOBHolder BLOBHolderPDF = new BLOBHolder();
				generaPDF gen = new generaPDF();
				gen.getPDF(filial, archivo.getXml().getBytes(), EXIT_ESTATUS,
						BLOBHolderPDF);
				System.out.println("Traigo del PDF Adobe:"
						+ (EXIT_ESTATUS.value.trim().equals("") ? "Vengo vacio"
								: EXIT_ESTATUS.value) + ",peso: "
						+ BLOBHolderPDF.value.getBinaryData().length);
				if (EXIT_ESTATUS.value.trim().equals("0")) {
					pdf.value = BLOBHolderPDF.value.getBinaryData();
					if (pdf.value.length > 0) {

						System.out.println("el pdf se llama ? " + xmls
								+ nombrePDF.concat(".pdf"));
						archPDF = new FileOutputStream(xmls
								+ nombrePDF.concat(".pdf"));

						rutaArchivoPDF = xmls + nombrePDF;
						archPDF.write(pdf.value);
						rutaCarpeta = xmls;
						listDocs.setNombreArchivo(nombrePDF);

						rutasPdf.add(new FileInputStream(new File(
								rutaArchivoPDF.concat(".pdf"))));
						if (archPDF != null) {
							archPDF.close();
							archPDF = null;
						}
					} else {
						EXIT_ESTATUS.value = "1";
						ERR_DESC.value = "Error al tratar de generar el pdf.";
						System.out
								.println("Error al tratar de generar el pdf.");
					}
				} else {

					EXIT_ESTATUS.value = "1";
					ERR_DESC.value = "Error al generar el pdf";
					// EXIT_ESTATUS.value = "Error al generar el pdf, "
					// + EXIT_ESTATUS.value;
					System.err.println("Error al generar el pdf");
				}
			} else {

				CallQuery CALLQ = getXml.loadProperties(PATHPROPERTIES);
				archivo = getXml.getDataFact(archivo, CALLQ);

				String cuae = oSF01.encode(archivo.getRfc().trim(), archivo
						.getNbuuid().trim(), archivo.getFechaEmision().trim(),
						archivo.getReferencia().trim());
				archivo.setCuae(cuae);
				String miRef = archivo.getReferencia().trim();
				String origen = miRef.substring(6, 12);
				StringHolder stringHolder = new StringHolder("-1");
				this.executeInvokePDF(archivo.getRfc(), archivo.getNbuuid(),
						archivo.getFechaEmision(), archivo.getCuae(), origen,
						stringHolder, pdf);
				System.out.println("pdf1: " + pdf.value.length
						+ ",stringHolder:" + stringHolder.value);
				if (pdf.value.length > 0) {

					archPDF = new FileOutputStream(xmls + nombrePDF + ".pdf");
					rutaArchivoPDF = xmls + nombrePDF;
					rutaCarpeta = xmls;
					listDocs.setNombreArchivo(nombrePDF + ".zip");
					archPDF.write(pdf.value);
					rutasPdf.add(new FileInputStream(new File(rutaArchivoPDF
							+ ".pdf")));
					if (archPDF != null) {
						archPDF.close();
						archPDF = null;
					}
				} else {
					EXIT_ESTATUS.value = "1";
					ERR_DESC.value = "Error al tratar de generar el pdf.";
					System.out.println("Error al tratar de generar el pdf.");
				}
			}

		} catch (Exception e) {
			EXIT_ESTATUS.value = "1";
			ERR_DESC.value = "Error al tratar de generar el pdf.";
			System.out.println("Error al tratar de generar el pdf.");
		}

	}

	private void crearXML(ArrayList<String> listaRefNoAden, String xmls,
			String tipoArchNombre, String avFilial) {
		BufferedWriter bw = null;
		GetXML getXML = new GetXML();
		try {

			CallQuery CALLQ = getXML.loadProperties(PATHPROPERTIES);
			int totalGen = 0;
			int conseXml = 1;
			for (int x = 0; x < listaRefNoAden.size(); x++) {

				String[] sepDatos = listaRefNoAden.get(x).split("\\|");

				// System.out.println(sepDatos[0]);
				// System.out.println(sepDatos[1]);

				String xmlRefNoAd = getXML.getXMLDeReferencias(sepDatos[0],
						sepDatos[1], CALLQ);

				String identRepltxt = sepDatos[2].replace(" ", "");
				String nombreArchivo = tipoArchNombre + "_" + identRepltxt
						+ "_" + conseXml + ".xml";

				conseXml++;
				File xml = new File(xmls + nombreArchivo);
				bw = new BufferedWriter(new FileWriter(xml));
				if (avFilial.equals("CON")) {
					bw.write(xmlRefNoAd);
					bw.flush();
				} else {
					bw.write(xmlRefNoAd);
					bw.flush();
					// bw.write(archivo.getXml());
				}
				totalGen = x;
			} // Finfor
			totalGen = totalGen + 1;
			System.out.println("Se generaron: " + totalGen
					+ " xml de constancias de " + listaRefNoAden.size());

		} catch (Exception e) {
			e.printStackTrace();
			// EXIT_ESTATUS.value = "1";
			// ERR_DESC.value = "Ocurrio un error al generar los archivos xml";

		}

	}

	public ArrayList<String> generarXMLConstancias(
			ArrayList<String> listatxIde, Referencia refe) {

		System.out.println(" ERR_DESC.value El servicio generarXMLConstancias archivos");
		ArrayList<String> listaRefNoAden = new ArrayList<String>();
		
		return listaRefNoAden;

	}

	public String validaGeneraXMLPDF(String tipoArchivo) {
		// 0 regresa xml, 1 regresa pdf, 2 regresa pdf-xml
		System.out.println("El servicio validaGeneraXMLPDF no disponible");
		String resultadoValidacion = "";

		return resultadoValidacion;

	}

	/**
	 * @param infoDocumentos
	 * @param pdf
	 * @param EXIT_STATUS
	 * @param ERR_DESC
	 * 
	 *            Metodo que recibe un arreglo de informacion con el cual genera
	 *            los pdf´s y los concatena Obtiene referencias con el metodo
	 *            getReferenciaDeConstancias Obtiene el XMl, genera los PDF
	 *            concatena en el metodo getDocuConstancias
	 * 
	 */
	public void getPDFDocumentosv2(InfoDocumentos infoDocumentos,
			ByteArrayHolder pdf, StringHolder EXIT_STATUS, StringHolder ERR_DESC) {
		System.out.println("Metodo caduco");
		EXIT_STATUS.value = "1";
		ERR_DESC.value = "Utilizar metodo getPDFDocumentos";

	}

	/**
	 * @param rfcReceptor
	 * @param tipoDocumento
	 * @param EXIT_STATUS
	 * @param ERR_DESC
	 * @return
	 * 
	 *         Metodo que recibe RFC y tipo de documento Manda a llamar al
	 *         metodo getAniosDocs
	 * 
	 */
	public InfoDocumentos getDatosDocumentosv2(String rfcReceptor, String anio,
			String tipoDocumento, String ideApli, StringHolder EXIT_STATUS,
			StringHolder ERR_DESC) {
		System.out.println("Metodo caduco getDatosDocumentosv2");
		EXIT_STATUS.value = "1";
		ERR_DESC.value = "Utilizar metodo getDatosDocumento";
		
		return null;
	}

	/**
	 * @param rfcReceptor
	 * @param tipoDocumento
	 * @param EXIT_STATUS
	 * @param ERR_DESC
	 * @return
	 * 
	 *         Metodo que recibe RFC y tipo de documento Manda a llamar al
	 *         metodo getAniosDocs
	 * 
	 */
	public InfoDocumentos getDatosDocumentos(String rfcReceptor,
			String tipoDocumento, StringHolder EXIT_STATUS,
			StringHolder ERR_DESC) {
		InfoDocumentos documentos = new InfoDocumentos();
		GetXML getXML = new GetXML();
		String etiqueta = "";
		try {
			if (rfcReceptor != null && !rfcReceptor.isEmpty()) {
				CallQuery CALLQ = getXML.loadProperties(PATHPROPERTIES);
				boolean ejecuta = true;
				if (tipoDocumento.trim().equals("1")) {
					etiqueta = "constancias";
				} else if (tipoDocumento.trim().equals("2")) {
					etiqueta = "facturas";
				} else {
					ejecuta = false;
					EXIT_STATUS.value = "1";
					ERR_DESC.value = "Introduzca valores de 1 para constancias y 2 para facturas";
					System.out
							.println("Introduzca valores de 1 para constancias y 2 para facturas");
				}
				if (ejecuta) {
					documentos = getXML.getAniosDocs(rfcReceptor,
							tipoDocumento, CALLQ);
					if (documentos != null) {
						EXIT_STATUS.value = "0";
						ERR_DESC.value = "0";
						System.out.println("OK");
					} else if (documentos == null) {
						EXIT_STATUS.value = "1";
						ERR_DESC.value = "No se encontraron " + etiqueta
								+ " con los parametros establecidos";
						System.out.println("No se encontraron " + etiqueta
								+ " con los parametros establecidos");
					}
				}
			} else {
				System.out
						.println("El valor del RFC no puede ser nulo o vacio");
				EXIT_STATUS.value = "1";
				ERR_DESC.value = "El valor del RFC no puede ser nulo o vacio";
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			EXIT_STATUS.value = "1";
			ERR_DESC.value = "No se pudieron obtener los documentos solicitados";

		}
		return documentos;
	}

	// METODO 2 OBTENER DOCUMENTOS PDF CONCATENADOS

	/**
	 * @param infoDocumentos
	 * @param pdf
	 * @param EXIT_STATUS
	 * @param ERR_DESC
	 * 
	 *            Metodo que recibe un arreglo de informacion con el cual genera
	 *            los pdf´s y los concatena Obtiene referencias con el metodo
	 *            getReferenciaDeConstancias Obtiene el XMl, genera los PDF
	 *            concatena en el metodo getDocuConstancias
	 * 
	 */
	public void getPDFDocumentos(InfoDocumentos infoDocumentos,
			ByteArrayHolder pdf, StringHolder EXIT_STATUS, StringHolder ERR_DESC) {
		System.out.println("Entra a generar PDF");
		GetXML getXML = new GetXML();
		Anio[] anios = infoDocumentos.getAnios();
		String pathGMC = "";
		Properties pr = new Properties();
		Properties sg = new Properties();
		String pathDwnldFile = "";
		Connection con = null;
		// Valido que el arreglo de años no venga vacio
		if (anios != null && anios.length > 0) {
			try {
				pr.load(new FileInputStream(PATHPROPERTIES));
				sg.load(new FileInputStream(pr.getProperty("PATH_GMC")));
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				CallQuery CALLQ = getXML.loadProperties(PATHPROPERTIES);
				pathGMC = pr.getProperty("PATH_GMC");
				pathDwnldFile = sg.getProperty("PATH_LOCAL_OUT_W6");
				String xmlRefer = null;
				StringBuffer sb = new StringBuffer();
				List<String> referenciasList = new ArrayList<String>();
				List<File> listOfPdfFiles = new ArrayList<File>();
				con = GetDataSource(CALLQ).getConnection();
				for (Anio infodocu : anios) {
					Documento[] docu = infodocu.getDocumentos();
					for (Documento documento : docu) {

						xmlRefer = getXML.getReferenciaDeConstanciasCon(con,
								documento.getReferencia());
						System.out.println("xmlRefer: " + xmlRefer);
						System.out
								.println("periodo: " + documento.getPeriodo());
						System.out.println("pathGMC: " + pathGMC);
						String arr[] = new String[8];
						arr[0] = pathGMC;
						arr[1] = xmlRefer;
						arr[2] = "0";
						arr[3] = documento.getPeriodo();
						arr[4] = "1";
						arr[5] = "";

						// HashMap mapRespuesta = CallFtp.ejecuta(arr);

						// ==========================================

						Connection conEDC = null;
						Connection conFAC = null;

						Properties prop = new Properties();
						prop.load(new FileInputStream(arr[0]));

						conEDC = dao.ConnectionBD.getConnectionBD(prop
								.getProperty("PATH_BD"));
						conFAC = dao.ConnectionBD.getConnectionBD(prop
								.getProperty("PATH_BD_FAC"));

						Ejecuta pdfeje = new Ejecuta();

						HashMap mapRespuesta = pdfeje.ejecuta(arr, conEDC,
								conFAC);
						conEDC.close();
						conFAC.close();

						// ==================================================

						String nomArchivo = "";
						String codigoRespuesta = "";
						if (mapRespuesta != null) {
							nomArchivo = (String) mapRespuesta
									.get("nombreArchivo");
							codigoRespuesta = (String) mapRespuesta
									.get("codigoRespuesta");

							if (codigoRespuesta.trim().equals("0")) {
								String nomPathArchivo = pathDwnldFile
										+ nomArchivo;
								File archivoPDF = new File(nomPathArchivo);
								if (archivoPDF.exists()) {
									listOfPdfFiles.add(archivoPDF);
								} else {
									EXIT_STATUS.value = "1";
									ERR_DESC.value = "El pdf no existe";
								}
							} else {
								EXIT_STATUS.value = "1";
								ERR_DESC.value = "Error al tratar de generar el pdf";
							}
						} else {
							EXIT_STATUS.value = "1";
							ERR_DESC.value = "Respuesta de pdf vacia o nula";
						}
						if (listOfPdfFiles.size() > 0) {
							pdf.value = concatenatePdfs(listOfPdfFiles,
									new File(cargaFechaNomArchivo()));
							if (pdf.value != null && pdf.value.length > 0) {
								EXIT_STATUS.value = "0";
								ERR_DESC.value = "";
							} else {
								EXIT_STATUS.value = "1";
								ERR_DESC.value = "El archivo no pudo generarse";
							}
						} else {
							EXIT_STATUS.value = "1";
							ERR_DESC.value = "El archivo no pudo generarse";
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				EXIT_STATUS.value = "1";
				ERR_DESC.value = "Los archivos no pudieron generarse";
			} finally {
				try {
					if (con != null) {
						con.close();
					}
				} catch (Exception ex) {
					EXIT_STATUS.value = "1";
					ERR_DESC.value = "Error al cerrar la conexion";
				}
			}
		} else {
			EXIT_STATUS.value = "1";
			ERR_DESC.value = "Criterios vacios o nulos";
		}
	}

	// TERCER METODO
	/**
	 * @param infoDocumentos
	 * @param email
	 * @param contrasenia
	 * @param EXIT_STATUS
	 * @param ERR_DESC
	 * 
	 *            Metodo que recibe arreglo de informacion y realiza el envio de
	 *            correo En el metodo getDocuConstancias genera los PDF, zipe y
	 *            envia
	 * 
	 */
	public void enviarCorreoDocumentos(InfoDocumentos infoDocumentos,
			String email, String contrasenia, StringHolder EXIT_STATUS,
			StringHolder ERR_DESC) {
		System.out.println("Inicia envio de correo");
		System.out.println("contrasenia: " + contrasenia);
		System.out.println("email: " + email);

		GetXML getXML = new GetXML();
		System.out.println("get Anios");
		Anio[] anios = infoDocumentos.getAnios();
		System.out.println("Anios: " + anios.length);
		ArrayList<String> listatxIde = new ArrayList<String>();
		String pathGMC = "";
		String rutaBusqueda = "";
		String rutaDestino = "";
		String urlH2H = "";
		String rutaMail = "";
		String buzon = "";
		String plantillaMail = "";
		String separador = "";
		String ambiente = "";
		String portTransfer = "";
		String ipTranfer = "";
		String userTransfer = "";
		String passTransfer = "";
		String jarSendMail = "";
		String pathConfig = "";
		String emisorMail = "";
		String nombreArchivo = "";
		String queryBusqRefCon = "";
		String asunto = "";
		if (anios != null) {
			try {

				Properties pr = new Properties();
				Properties sg = new Properties();

				try {

					pr.load(new FileInputStream(PATHPROPERTIES));
					sg.load(new FileInputStream(pr.getProperty("PATH_GMC")));
					separador = "/";

				} catch (Exception e) {

					pr.load(new FileInputStream(
							"C:\\Pruebas\\querysFacturasCFDI.properties")); // PRUEBAS
					sg.load(new FileInputStream(pr.getProperty("PATH_GMC")));
					separador = "\\";

				}
				pathGMC = pr.getProperty("PATH_GMC");
				rutaDestino = pr.getProperty("RUTA_DESTINO");
				urlH2H = pr.getProperty("URL_H2H");
				rutaMail = pr.getProperty("RUTA_MAIL");
				buzon = pr.getProperty("BUZON");
				plantillaMail = pr.getProperty("plantillaMail");
				ambiente = pr.getProperty("Ambiente");
				portTransfer = pr.getProperty("portTransfer");
				ipTranfer = pr.getProperty("ipTranfer");
				userTransfer = pr.getProperty("userTransfer");
				passTransfer = pr.getProperty("passTransfer");
				jarSendMail = pr.getProperty("jarSendMail");
				pathConfig = pr.getProperty("pahtConfig");
				emisorMail = pr.getProperty("EmisorMail");
				nombreArchivo = "";
				queryBusqRefCon = pr.getProperty("BusqRefCon");
				asunto = pr.getProperty("ASUNTO_CONS");
				CallQuery CALLQ = getXML.loadProperties(PATHPROPERTIES);
				String xmlRefer = null;

				StringBuffer sb = new StringBuffer();
				List<String> referenciasList = new ArrayList<String>();

				for (Anio infodocu : anios) {
					Documento[] docu = infodocu.getDocumentos();

					for (Documento documento : docu) {

						xmlRefer = getXML.getReferenciaDeConstancias(
								documento.getReferencia(), CALLQ);
						xmlRefer = documento.getReferencia();

						// String xmlRefer2 = documento.getReferencia();
						listatxIde.add(documento.getTxIdentificador());

						if (!referenciasList.contains(xmlRefer + ","
								+ documento.getPeriodo() + "|")) {
							if (xmlRefer != null && !xmlRefer.isEmpty()) {
								referenciasList.add(xmlRefer + ","
										+ documento.getPeriodo() + "|");
							}
						}

					}
				}

				for (String x : referenciasList) {
					sb.append(x);
				}
				System.out.println("getDocuConstancias");
				HashMap<String, Object> resp = getDocuConstanciasCorreo(email,
						contrasenia, "", "", "", "", "", "8", sb.toString(),
						"", "", EXIT_STATUS, ERR_DESC, listatxIde, "",
						queryBusqRefCon);
				EXIT_STATUS.value = (String) resp.get("EXIT_ESTATUS");
				ERR_DESC.value = (String) resp.get("ERR_DESC");

				String flagh2h = pr.getProperty("FLAG_H2H");
				rutaBusqueda = pr.getProperty("BUSQUEDA");

				if (EXIT_STATUS.value.trim().equals("0")) {

					String properties = PATHPROPERTIES;
					System.out.println("procesaArchivosConstanciasCorreo: "
							+ properties);
					String referencias = (String) resp.get("referencias");
					@SuppressWarnings("unchecked")
					ArrayList<String> listaRefNoAden = (ArrayList<String>) resp
							.get("listaRefNoAden");
					System.out.println("Antes del inicio del hilo: ");

					EnvCorr envCorr = new EnvCorr(referencias, plantillaMail,
							properties, flagh2h, pathGMC, rutaBusqueda,
							rutaDestino, urlH2H, rutaMail, buzon, email,
							contrasenia, this.CMD, listaRefNoAden, ambiente,
							portTransfer, ipTranfer, userTransfer,
							passTransfer, jarSendMail, pathConfig, emisorMail,
							nombreArchivo, asunto, CALLQ);
					envCorr.start();

					System.out
							.println("procesaArchivosConstanciasCorreo libero thread");
					EXIT_STATUS.value = "0";
					ERR_DESC.value = "";
				}
				System.out.println("getDocuConstancias aoout");
			} catch (Exception e) {
				e.printStackTrace();
				EXIT_STATUS.value = "1";
				ERR_DESC.value = "El correo no se pudo enviar";
			}

		}
	}

	/**
	 * @param email
	 * @param contrasenia
	 * @param folioCFD
	 * @param fechaTimbrado
	 * @param fechaInicio
	 * @param fechaFinal
	 * @param periodo
	 * @param tipoArchivo
	 * @param referencias
	 * @param opcion
	 * @param aplicativo
	 * @param EXIT_ESTATUS
	 * @param ERR_DESC
	 * @return
	 * 
	 *         Llena variables para poder procesar ya sea el XML o PDF En el
	 *         metodo procesaArchivosConstancias genera los PDF´s XML´s y
	 *         envia por correo dependiendo de las opciones
	 * 
	 * 
	 */
	private HashMap<String, Object> getDocuConstanciasCorreo(String email,
			String contrasenia, String folioCFD, String fechaTimbrado,
			String fechaInicio, String fechaFinal, String periodo,
			String tipoArchivo, String referencias, String opcion,
			String aplicativo, StringHolder EXIT_ESTATUS,
			StringHolder ERR_DESC, ArrayList<String> listatxIde, String idApli,
			String queryBusqRefCon) {
		System.out.println("fechaFinal: " + fechaFinal);
		System.out.println("periodo: " + periodo);
		System.out.println("tipoArchivo: " + tipoArchivo);
		System.out.println("referencias: " + referencias);
		System.out.println("opcion: " + opcion);
		System.out.println("aplicativo: " + aplicativo);
		HashMap<String, Object> respMap = new HashMap<String, Object>();
		GetXML getXML = new GetXML();
		List<Archivo> archivos = null;
		ArrayList<String> listaRefNoAden = new ArrayList<String>();
		int xi = 0;
		boolean ejecutaXML = false;
		boolean ejecutaPDF = false;
		Connection con = null;
		ResultSet rsRef = null;
		PreparedStatement pStmRef = null;
		byte[] document = null;
		String queryRef = "";
		CallQuery CALLQ = getXML.loadProperties(PATHPROPERTIES);
		try {
			if ((tipoArchivo == null || tipoArchivo.isEmpty())
					&& (referencias == null || referencias.isEmpty())) {
				EXIT_ESTATUS.value = "1";
				ERR_DESC.value = "Se deben de colocar los criterios de busqueda";
			} else {
				if (tipoArchivo != null && !tipoArchivo.isEmpty()) {
					if (tipoArchivo.trim().equals("0")) {
						ejecutaXML = true;
						ejecutaPDF = false;
					} else if (tipoArchivo.trim().equals("1")) {
						ejecutaXML = false;
						ejecutaPDF = true;
					} else if (tipoArchivo.trim().equals("2")) {
						ejecutaXML = true;
						ejecutaPDF = true;
					} else {
						ejecutaXML = true;
						ejecutaPDF = true;
					}
				} else {
					ejecutaXML = true;
					ejecutaPDF = true;
				}

				if (referencias != null && !referencias.isEmpty()) {
					if (verificaReferencias(referencias)) {

						HashMap<String, String> resMap = getRefereenciasCorreo(referencias);
						if (resMap != null) {
							String mReferencias = resMap.get("referencias");
							String mPeriodos = resMap.get("periodos");
							String mTxIden = getTXIdenComp(listatxIde);
							con = GetDataSource(CALLQ).getConnection();
							queryBusqRefCon = queryBusqRefCon.replace("*p",
									mPeriodos);
							queryBusqRefCon = queryBusqRefCon.replace("*r",
									mReferencias);
							queryBusqRefCon = queryBusqRefCon.replace("*t",
									mTxIden);
							// System.out.println("query................:"+queryBusqRefCon);
							pStmRef = con.prepareStatement(queryBusqRefCon);

							rsRef = pStmRef.executeQuery();

							if (rsRef != null) {
								while (rsRef.next()) {
									listaRefNoAden.add(rsRef.getString(1) + "|"
											+ rsRef.getString(2) + "|"
											+ rsRef.getString(15));
								}
							}

							if (rsRef != null) {
								rsRef.close();
							}
							if (pStmRef != null) {
								pStmRef.close();
							}

							// System.out.println("Entro 8........... ");
							respMap.put("archivos", archivos);
							respMap.put("ejecutaPDF", new Boolean(ejecutaPDF));
							respMap.put("ejecutaXML", new Boolean(ejecutaXML));
							respMap.put("email", email);
							respMap.put("contrasenia", contrasenia);
							respMap.put("periodo", periodo);
							respMap.put("tipoArchivo", tipoArchivo);
							respMap.put("listaRefNoAden", listaRefNoAden);
							respMap.put("referencias", referencias);
							respMap.put("idApli", idApli);
							respMap.put("EXIT_ESTATUS", "0");
							respMap.put("ERR_DESC", "");
							// System.out.println("cargo map........... ");
							if (rsRef != null) {
								rsRef.close();
							}
							if (pStmRef != null) {
								pStmRef.close();
							}
							if (con != null) {
								con.close();
							}
							return respMap;
						}

					} else {
						respMap.put("ERR_DESC",
								"No se encontraron archivos con esos criterios");
						respMap.put("EXIT_ESTATUS", "1");
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ENtro error: " + e.getMessage());
			respMap.put("ERR_DESC",
					"El proceso de envío o concatenación de archivos no se completó.");
			respMap.put("EXIT_ESTATUS", "1");
		} finally {
			try {
				if (rsRef != null) {
					rsRef.close();
				}
				if (pStmRef != null) {
					pStmRef.close();
				}
				if (con != null) {
					con.close();
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return respMap;
	}

	private byte[] getDocuConstancias(String email, String contrasenia,
			String folioCFD, String fechaTimbrado, String fechaInicio,
			String fechaFinal, String periodo, String tipoArchivo,
			String referencias, String opcion, String aplicativo,
			StringHolder EXIT_ESTATUS, StringHolder ERR_DESC,
			ArrayList<String> listatxIde, String idApli) {
		System.out.println("fechaFinal: " + fechaFinal);
		System.out.println("periodo: " + periodo);
		System.out.println("tipoArchivo: " + tipoArchivo);
		System.out.println("referencias: " + referencias);
		System.out.println("opcion: " + opcion);
		System.out.println("aplicativo: " + aplicativo);
		byte[] document = null;
		GetXML getXML = new GetXML();
		List<Archivo> archivos = null;
		ArrayList<String> listaRefNoAden = new ArrayList<String>();
		int xi = 0;
		boolean ejecutaXML = false;
		boolean ejecutaPDF = false;
		try {
			CallQuery CALLQ = getXML.loadProperties(PATHPROPERTIES);
			if ((tipoArchivo == null || tipoArchivo.isEmpty())
					&& (referencias == null || referencias.isEmpty())) {
				EXIT_ESTATUS.value = "1";
				ERR_DESC.value = "Se deben de colocar los criterios de busqueda";
				ERR_DESC.value = "Se deben de colocar los criterios de busqueda";
			} else {
				if (tipoArchivo != null && !tipoArchivo.isEmpty()) {
					if (tipoArchivo.trim().equals("0")) {
						ejecutaXML = true;
						ejecutaPDF = false;
					} else if (tipoArchivo.trim().equals("1")) {
						ejecutaXML = false;
						ejecutaPDF = true;
					} else if (tipoArchivo.trim().equals("2")) {
						ejecutaXML = true;
						ejecutaPDF = true;
					} else {
						ejecutaXML = true;
						ejecutaPDF = true;
					}
				} else {
					ejecutaXML = true;
					ejecutaPDF = true;
				}

				if (referencias != null && !referencias.isEmpty()) {
					if (verificaReferencias(referencias)) {
						List<Referencia> ref = getRefereencias(referencias);
						if (ref != null && ref.size() > 0) {
							archivos = new ArrayList<Archivo>();
							String xmlDato = "";
							for (Referencia refe : ref) {
								periodo = refe.getPeriodo();
								String avFilial = refe.getReferencia().trim()
										.substring(9, 12);
								String xmlCon = "";
								if (avFilial != null && !avFilial.isEmpty()
										&& avFilial.equals("CON")) {
									String referencia = refe.getReferencia();

									if (referencia != null
											&& !referencia.isEmpty()) {
										xmlDato = getXML.getXMLDeReferencias(
												referencia, refe.getPeriodo(),
												CALLQ);
										xmlCon = getXML.getXMLDeReferencias(
												refe.getReferencia(),
												refe.getPeriodo(), CALLQ);
										xmlDato = convertAcentosXML(xmlDato);
									}
								} else {
									xmlDato = getXML.getXMLDeReferencias(
											refe.getReferencia(),
											refe.getPeriodo(), CALLQ);
									xmlDato = convertAcentosXML(xmlDato);

								}
								if (xmlDato != null && !xmlDato.isEmpty()) {

									if (avFilial != null && !avFilial.isEmpty()
											&& avFilial.equals("CON")) {
										// System.out.println("xmlDato CON "
										// + xmlDato);
										archivos.add(new Archivo(refe
												.getPeriodo(), xmlDato, refe
												.getReferencia(), refe
												.getPeriodo(), xmlCon,
												tipoArchivo));
									} else {
										// System.out.println("xmlDato * "
										// + xmlDato);
										archivos.add(new Archivo(refe
												.getPeriodo(), xmlDato, refe
												.getReferencia(), refe
												.getPeriodo(), tipoArchivo));
									}
								}

								// Obtener Referencias no Addenda
								if (tipoArchivo.equalsIgnoreCase("2")) {

									try {
										Connection con = null;
										String queryRef = "";
										ResultSet rsRef = null;
										PreparedStatement pStmRef = null;
										// String PATHPROPERTIES =
										// "D:\\ProyectosEnCurso\\ServicioMedcCambios\\querysFacturasCFDI.properties";

										// con =
										// GetDataSource().getConnection();

										if (CALLQ.getBANDERA_DB().equals("0")) {
											con = GetDataSource(CALLQ)
													.getConnection();
										} else {
											con = ConeccionPruebas
													.getThreadConexion92("/home/jemed/daniel/");
										}

										String txtIden = listatxIde.get(xi);
										xi++;

										queryRef = CALLQ.getQueryBusRef();
										pStmRef = con
												.prepareStatement(queryRef);
										pStmRef.setString(1, refe.getPeriodo());
										pStmRef.setString(2,
												refe.getReferencia());
										// System.out.println("Referencia: "+refe.getReferencia());
										// System.out.println("Periodo: "+refe.getPeriodo());

										int posIdent = txtIden.lastIndexOf("_");

										// System.out.println("Identificador: "+
										// txtIden.substring(0, posIdent));
										pStmRef.setString(3,
												txtIden.substring(0, posIdent));
										rsRef = pStmRef.executeQuery();

										if (rsRef != null) {
											while (rsRef.next()) {
												if (rsRef
														.getString(1)
														.equalsIgnoreCase(
																refe.getReferencia())) {

												} else {
													listaRefNoAden
															.add(rsRef
																	.getString(1)
																	+ "|"
																	+ refe.getPeriodo()
																	+ "|"
																	+ rsRef.getString(15));
													// xml =
													// readClob2(rs.getClob(1).getAsciiStream());
												}
											}
										}
										if (rsRef != null) {
											rsRef.close();
										}
										if (pStmRef != null) {
											pStmRef.close();
										}

										if (con != null) {
											con.close();
											con = null;
										}

									} catch (Exception e) {
										e.printStackTrace();
										System.out
												.println("No pude generar xml");
									}

								}

							}

							if (archivos != null && archivos.size() > 0) {
								System.out.println("archivos: "
										+ archivos.size());
								if (tipoArchivo.trim().equals("1")) {// INICIO
																		// VALIDACION
																		// TIPO
																		// ARCHIVO
									document = procesaArchivosConstancias(
											archivos, ejecutaPDF, ejecutaXML,
											"", "", "", tipoArchivo,
											EXIT_ESTATUS, ERR_DESC, null,
											referencias, idApli);
								} else if (tipoArchivo.trim().equals("2")) {
									document = procesaArchivosConstancias(
											archivos, ejecutaPDF, ejecutaXML,
											email, contrasenia, periodo,
											tipoArchivo, EXIT_ESTATUS,
											ERR_DESC, listaRefNoAden,
											referencias, idApli);
								} // FINALIZO VALIDACION TIPO ARCHIVO
							} else {
								System.out
										.println("No se encontraron archivos con esos criterios.");
								EXIT_ESTATUS.value = "1";
								ERR_DESC.value = "No se encontraron archivos con esos criterios.";
							}
						}
					} else {
						EXIT_ESTATUS.value = "1";
						ERR_DESC.value = "Las referencias son incorrectas.";
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ENtro error: " + e.getMessage());
			EXIT_ESTATUS.value = "1";
			ERR_DESC.value = "El proceso de envío o concatenación de archivos no se completó.";
		}
		return document;
	}

	/**
	 * @param archivos
	 * @param ejecutaPDF
	 * @param ejecutaXML
	 * @param email
	 * @param contrasenia
	 * @param periodo
	 * @param tipoArchivo
	 * @param EXIT_ESTATUS
	 * @return
	 * @throws Exception
	 * 
	 *             Genera PDF´s, XML´s y concatena o envia dependiendo sea el
	 *             caso
	 */
	private byte[] procesaArchivosConstancias(List<Archivo> archivos,
			boolean ejecutaPDF, boolean ejecutaXML, String email,
			String contrasenia, String periodo, String tipoArchivo,
			StringHolder EXIT_ESTATUS, StringHolder ERR_DESC,
			ArrayList<String> listaRefNoAden, String referencias,
			String plantillaMail) throws Exception {
		byte[] respuesta = null;

		String horaIni = obtenerHora();
		String operacion = "";
		String aplicativo = "";
		int numXML = 0;
		int numPDFExi = 0;
		int numErr = 0;
		String tipoDoc = "";
		String viaPDF = "";

		String avFilial = "";
		GetXML getXML = new GetXML();
		CallQuery CALLQ = getXML.loadProperties(PATHPROPERTIES);
		ListaDocumentos listDocs = new ListaDocumentos();
		String xmls = new String();
		// String xmlsZip = "";
		File directorio = null;
		int cont = 1;
		BufferedWriter bw = null;
		GetXML getxml = new GetXML();
		String rutaArchivoPDF = "";
		String rutaCarpeta = "";
		List<InputStream> rutasPdf = new ArrayList<InputStream>();
		Random rnd = new Random();
		long curr = System.currentTimeMillis();
		String gt = Long.toString(curr);
		String rn = Integer.toString((int) (rnd.nextDouble() * 5000 + 0));
		xmls = "/tmp/".concat("xmls").concat("_").concat(gt).concat("_")
				.concat(rn).concat("/");
		// xmlsZip =
		// "/tmp/".concat("xmlsZip").concat("_").concat(gt).concat("_").concat(rn);
		System.out.println("xmlsDirectory: " + xmls);
		directorio = new File(xmls);
		directorio.mkdirs();
		directorio.setExecutable(true);
		directorio.setReadable(true);
		directorio.setWritable(true);
		listDocs.setRutaCarpeta(xmls);
		Properties pr = new Properties();
		Properties sg = new Properties();
		boolean ejecutaGMC = false;
		boolean ejecutaGMCPrin = false;
		Integer eGMC = null;
		String fechaGMC = "";
		String procGMC = "";
		String pathDownLoadGMC = "";
		String pathServer = "";
		String separador = "";
		String pathGMC = "";
		generaPDF gen = new generaPDF("basura");
		if (archivos.size() >= 1) {
			directorio = new File(xmls);
			String tipoArchNombre = null;
			FileOutputStream archPDF = null;
			try {
				pr.load(new FileInputStream(this.PATHPROPERTIES));
				sg.load(new FileInputStream(pr.getProperty("PATH_GMC")));
				pathGMC = pr.getProperty("PATH_GMC");
				separador = "/";
				eGMC = new Integer(pr.getProperty("EJECUTA_GMC"));
				fechaGMC = pr.getProperty("FECHA_GMC");
				procGMC = pr.getProperty("PROC_GMC");
				if (eGMC != null) {
					if (eGMC.intValue() == 1) {
						ejecutaGMCPrin = true;
					}
				}
				pathDownLoadGMC = sg.getProperty("PATH_LOCAL_OUT_W6");
				pathServer = sg.getProperty("URL_SERVER");
			} catch (Exception e) {

				pr.load(new FileInputStream(
						"C:\\Pruebas\\querysFacturasCFDI.properties")); // PRUEBAS
				sg.load(new FileInputStream(pr.getProperty("PATH_GMC")));
				separador = "\\";

			}
			for (Archivo archivo : archivos) {
				try {
					String referencia = archivo.getReferencia();
					if (referencia != null && !referencia.isEmpty()) {
						if (ejecutaGMCPrin) {
							if (!ejecutaGMC(referencia, procGMC, fechaGMC)) {
								ejecutaGMC = false;
							} else {
								ejecutaGMC = true;
							}
						}
						String filial = referencia.substring(6, 12);
						avFilial = referencia.substring(9, 12);

						if (tipoArchivo.trim().equals("1")
								|| tipoArchivo.trim().equals("2")) {

							if (ejecutaPDF) {
								ByteArrayHolder pdf = new ByteArrayHolder(
										new byte[1024]);
								SF01 oSF01 = new SF01();

								if (archivo.getRfc().equals("1")) {
									tipoArchNombre = "Constancia";
								} else if (archivo.getRfc().equals("2")) {
									tipoArchNombre = "Constancia";
								} else {
									tipoArchNombre = "Constancia";
								}
								tipoDoc = tipoArchNombre;
								String nombrePDF = tipoArchNombre + "_"

								+ System.nanoTime() + "_" + cont;
								if (avFilial.equals("CON")) {
									if (ejecutaGMC) {
										pdf.value = gen.getPDFGMC(pathGMC,
												pathDownLoadGMC, pathServer,
												referencia, EXIT_ESTATUS, null,
												null);
										viaPDF = "GMC";
									} else {
										viaPDF = "ADOBE";
										BLOBHolder BLOBHolderPDF = new BLOBHolder();
										// generaPDF gen = new generaPDF();
										gen.getPDFCCC(filial, archivo.getXml()
												.getBytes(), EXIT_ESTATUS,
												BLOBHolderPDF);
										System.out
												.println("Traigo del PDF Constancias:"
														+ (EXIT_ESTATUS.value
																.trim().equals(
																		"") ? "Vengo vacio"
																: EXIT_ESTATUS.value)
														+ ",peso: "
														+ BLOBHolderPDF.value
																.getBinaryData().length);
										pdf.value = BLOBHolderPDF.value
												.getBinaryData();
									}
									if (EXIT_ESTATUS.value.trim().equals("0")) {

										if (pdf.value.length > 0) {
											numPDFExi++;
											System.out
													.println("el pdf se llama ? "
															+ xmls
															+ nombrePDF
																	.concat(".pdf"));
											archPDF = new FileOutputStream(xmls
													+ nombrePDF.concat(".pdf"));

											rutaArchivoPDF = xmls + nombrePDF;
											archPDF.write(pdf.value);
											rutaCarpeta = xmls;
											listDocs.setNombreArchivo(nombrePDF);

											rutasPdf.add(new FileInputStream(
													new File(rutaArchivoPDF
															.concat(".pdf"))));
											if (archPDF != null) {
												archPDF.close();
												archPDF = null;
											}
										} else {
											numErr++;
											EXIT_ESTATUS.value = "1";
											ERR_DESC.value = "Error al tratar de generar el pdf.";
										}
									} else {
										numErr++;
										EXIT_ESTATUS.value = "1";
										ERR_DESC.value = "Error al tratar de generar el pdf.";
									}
								} else {

									archivo = getxml
											.getDataFact(archivo, CALLQ);

									String cuae = oSF01.encode(archivo.getRfc()
											.trim(),
											archivo.getNbuuid().trim(), archivo
													.getFechaEmision().trim(),
											archivo.getReferencia().trim());
									archivo.setCuae(cuae);
									String miRef = archivo.getReferencia()
											.trim();
									String origen = miRef.substring(6, 12);
									StringHolder stringHolder = new StringHolder(
											"-1");
									this.executeInvokePDF(archivo.getRfc(),
											archivo.getNbuuid(),
											archivo.getFechaEmision(),
											archivo.getCuae(), origen,
											stringHolder, pdf);
									System.out.println("pdf1: "
											+ pdf.value.length
											+ ",stringHolder:"
											+ stringHolder.value);
									if (pdf.value.length > 0) {

										archPDF = new FileOutputStream(xmls
												+ nombrePDF + ".pdf");
										rutaArchivoPDF = xmls + nombrePDF;
										rutaCarpeta = xmls;
										listDocs.setNombreArchivo(nombrePDF
												+ ".zip");
										archPDF.write(pdf.value);
										rutasPdf.add(new FileInputStream(
												new File(rutaArchivoPDF
														+ ".pdf")));
										if (archPDF != null) {
											archPDF.close();
											archPDF = null;
										}
									} else {
										EXIT_ESTATUS.value = "1";
										ERR_DESC.value = "Error al tratar de generar el pdf.";
									}
								}
							}

						} // FINALIZO VALIDACION SI CONCATENO O ENVIO ARCHIVOS

						cont++;
					}
				} catch (Exception ex) {
					ex.printStackTrace();
					EXIT_ESTATUS.value = "1";
					ERR_DESC.value = "Error al tratar de generar los archivos";
				} finally {
					if (bw != null) {
						bw.close();
					}
					if (archPDF != null) {
						archPDF.close();
					}
				}
			}

			try {
				if (tipoArchivo.trim().equals("2")) {
					if (ejecutaXML) {
						int totalGen = 0;
						int conseXml = 1;
						for (int x = 0; x < listaRefNoAden.size(); x++) {

							String[] sepDatos = listaRefNoAden.get(x).split(
									"\\|");

							// System.out.println(sepDatos[0]);
							// System.out.println(sepDatos[1]);

							String xmlRefNoAd = getXML.getXMLDeReferencias(
									sepDatos[0], sepDatos[1], CALLQ);

							String identRepltxt = sepDatos[2].replace(" ", "");
							String nombreArchivo = tipoArchNombre + "_"
							// + System.nanoTime() + "_" + conseXml
									+ identRepltxt + "_" + conseXml + ".xml";

							conseXml++;
							File xml = new File(xmls + nombreArchivo);
							bw = new BufferedWriter(new FileWriter(xml));
							if (avFilial.equals("CON")) {
								bw.write(xmlRefNoAd);
								bw.flush();
								// bw.write(archivo.getXml());
								// bw.write(archivo.getXmlConstancia());
							} else {
								bw.write(xmlRefNoAd);
								bw.flush();
								// bw.write(archivo.getXml());
							}
							totalGen = x;
						} // Finfor
						totalGen = totalGen + 1;
						numXML = totalGen;
						System.out.println("Se generaron: " + totalGen
								+ " xml de constancias de "
								+ listaRefNoAden.size());
					} // end if
				}

			} catch (Exception e) {
				e.printStackTrace();
				EXIT_ESTATUS.value = "1";
				ERR_DESC.value = "Ocurrio un error al generar los archivos xml";

			}
		}

		if (tipoArchivo.trim().equals("1")) {
			OutputStream out = new FileOutputStream(new File(rutaArchivoPDF
					+ "_Documentos.pdf"));
			xmls = rutaArchivoPDF + "_Documentos.pdf";
			// System.out.println("xmls" + xmls);
			if (rutasPdf.size() >= 1) {

				Document document = new Document();
				PdfWriter writer = PdfWriter.getInstance(document, out);
				document.open();
				PdfContentByte cb = writer.getDirectContent();

				for (InputStream in : rutasPdf) {
					PdfReader reader = new PdfReader(in);
					for (int i = 1; i <= reader.getNumberOfPages(); i++) {
						document.newPage();
						PdfImportedPage page = writer
								.getImportedPage(reader, i);
						cb.addTemplate(page, 0, 0);
					}
				}

				out.flush();
				document.close();
				out.close();

				InputStream io = new FileInputStream(xmls);

				byte[] bytesPDF = readFully(io);

				respuesta = bytesPDF;
				operacion = "ConcatenacionPDF";
			}
		} else if (tipoArchivo.trim().equals("2")) {
			try {
				if (CALLQ.getAmbiente().equals("Produccion")) {

					if (!plantillaMail.isEmpty()) {

						aplicativo = plantillaMail;
						plantillaMail = getPlantillaMail(plantillaMail);

					} else {
						plantillaMail = CALLQ.getnPlantilla();
						aplicativo = "No_Disponible";
					}

					Mail mail = new Mail(CALLQ.getPahtConfig(), plantillaMail,
							CALLQ.getEmisorMail(), email, xmls,
							listDocs.getNombreArchivo(), contrasenia, periodo);
					mail.sendMail();

					operacion = "EnvioMail";

					EXIT_ESTATUS.value = "0";
					ERR_DESC.value = "0";
				} else if (CALLQ.getAmbiente().equals("Desarrollo")) {

					// MOVER ARCHIVOS A PRODUCCION
					String puertoMailString = CALLQ.getPortTransfer();
					int puertoMail = Integer.parseInt(puertoMailString);
					File xmlsFolder = new File(xmls);
					File carpTemp = null;

					if (xmlsFolder.exists()) {
						if (xmlsFolder.isDirectory()) {

							carpTemp = new File(CALLQ.getCarpetaTempMail()
									+ System.nanoTime() + "/");
							if (!carpTemp.exists()) {
								String cmdFolder = "mkdir -m775" + " "
										+ carpTemp.toString();

								Utilerias.ejecutarSSH(CALLQ.getIpTransfer(),
										CALLQ.getUserTransfer(),
										CALLQ.getPassTransfer(), puertoMail,
										cmdFolder, true);

								System.out.println("Carpeta Creada: "
										+ carpTemp);
							}

							for (File f : xmlsFolder.listFiles()) {
								ExecutionSSH exeSSH = new ExecutionSSH(
										CALLQ.getIpTransfer(),
										CALLQ.getUserTransfer(),
										CALLQ.getPassTransfer(), puertoMail);
								String nameFile = f.getName().toString();
								// exeSSH.putFileInServer(f.getAbsolutePath(),
								// carpTemp+nameFile);
								exeSSH.putFileInServer(f.getAbsolutePath(),
										carpTemp + "/" + nameFile);
							}

						} else {
							System.out.println("No es directorio: " + xmls);
						}
					} else {

						System.out.println("La carpeta temporal no existe");
					}
					//
					if (plantillaMail.isEmpty()) {
						plantillaMail = getPlantillaMail(plantillaMail);
					} else {
						plantillaMail = CALLQ.getnPlantilla();
					}

					String cmd = "java -jar" + " " + CALLQ.getJarSendMail()
							+ " " + CALLQ.getPahtConfig() + " " + plantillaMail
							+ " " + CALLQ.getEmisorMail() + " " + email
							+ " "
							+ carpTemp // "/export/home/jemed/"
							+ " " + listDocs.getNombreArchivo() + " "
							+ contrasenia + " " + Tools.getMes(periodo);
					// // + periodo.substring(0, 4);
					// Runtime.getRuntime().exec(cmd);

					Utilerias.ejecutarSSH(CALLQ.getIpTransfer(),
							CALLQ.getUserTransfer(), CALLQ.getPassTransfer(),
							puertoMail, cmd, true);

					String cmdFoldFiles = "rm -r" + " " + carpTemp.toString();
					String resCMD = Utilerias.ejecutarSSH(
							CALLQ.getIpTransfer(), CALLQ.getUserTransfer(),
							CALLQ.getPassTransfer(), puertoMail, cmdFoldFiles,
							true);
					System.out.println("ResCMDRM: " + resCMD);
					System.out.println("Envio de correo finalizado");
					operacion = "EnvioMailDesarrollo";
				}

				crearBit(horaIni, aplicativo, operacion, numXML, numPDFExi,
						numErr, tipoDoc, viaPDF);

			} catch (Exception e) {
				EXIT_ESTATUS.value = "1";
				ERR_DESC.value = "El servicio de envío de correo no esta disponible";

				String nameFile = null;
				File folder = new File(rutaCarpeta);
				File[] listOfFiles = folder.listFiles();
				File fileDelete = null;
				System.out.println("Elimina archivos.");

				for (int i = 0; i < listOfFiles.length; i++) {
					nameFile = listOfFiles[i].getName();
					fileDelete = new File(rutaCarpeta + nameFile);
					fileDelete.delete();
				}
				folder.delete();

				directorio.delete();

				listDocs.setRutaDocumento(rutaCarpeta);
			}
		}// Fin Else If

		String nameFile = null;
		File folder = new File(rutaCarpeta);
		File[] listOfFiles = folder.listFiles();
		File fileDelete = null;
		System.out.println("Elimina archivos.");
		if (listOfFiles != null) {
			for (int i = 0; i < listOfFiles.length; i++) {
				nameFile = listOfFiles[i].getName();
				fileDelete = new File(rutaCarpeta + nameFile);
				fileDelete.delete();
			}
		}
		folder.delete();

		directorio.delete();

		listDocs.setRutaDocumento(rutaCarpeta);

		return respuesta;
	}

	static DataSource GetDataSource(CallQuery CALLQ) throws NamingException {
		InitialContext initContext = new InitialContext();
		Context envContext = (Context) initContext.lookup("java:comp/env");
		return (DataSource) envContext.lookup(CALLQ.getJndiFac());
	}

	static DataSource GetDataSourceEDC(CallQuery CALLQ) throws NamingException {
		InitialContext initContext = new InitialContext();
		System.out.println("Se intenta hacer lookup al jndiEDC: "
				+ CALLQ.getJndiEdc());
		Context envContext = (Context) initContext.lookup("java:comp/env");
		return (DataSource) envContext.lookup(CALLQ.getJndiEdc());
	}

	public static byte[] readFully(InputStream stream) throws IOException {
		byte[] buffer = new byte[8192];
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		int bytesRead;
		while ((bytesRead = stream.read(buffer)) != -1) {
			baos.write(buffer, 0, bytesRead);
		}
		return baos.toByteArray();
	}

	public void paperlessSecurity(String contrasenia, StringHolder RESPUESTA,
			StringHolder EXIT_ESTATUS, StringHolder ERR_DESC) {

			EXIT_ESTATUS.value = "0";
			RESPUESTA.value = "El serivicio paperlessSecurity no esta disponible";
			ERR_DESC.value = "";

	}

	private boolean ejecutaGMC(String referencia, String filiales, String fecha) {
		String[] sFiliales = null;
		String fili = "";
		boolean ejecutaGMC = false;
		System.out.println("*****************ejecutaGMC referencia:"
				+ referencia);
		System.out.println("*****************ejecutaGMC fecha :" + fecha);
		System.out.println("*****************ejecutaGMC filiales :" + filiales);
		Calendar fechaProperties = Calendar.getInstance();
		Calendar fechaArchivo = Calendar.getInstance();
		if (filiales != null && !filiales.isEmpty()) {
			sFiliales = filiales.split(",");
			fili = referencia.substring(6, 12);
			if (sFiliales != null && sFiliales.length > 0) {
				for (String filial : sFiliales) {
					if (filial.equals(fili)) {
						ejecutaGMC = true;
						break;
					}
				}
				if (ejecutaGMC) {
					String[] sFecha = fecha.split("/");
					if (sFecha.length == 3) {
						int anio = new Integer(sFecha[2].trim()).intValue();
						int mes = new Integer(sFecha[1].trim()).intValue();
						int dia = new Integer(sFecha[0].trim()).intValue();
						fechaProperties.set(Calendar.DATE, dia);
						fechaProperties.set(Calendar.MONTH, (mes - 1));
						fechaProperties.set(Calendar.YEAR, anio);
						anio = new Integer("20".concat(referencia.substring(0,
								2))).intValue();
						mes = new Integer(referencia.substring(2, 4))
								.intValue();
						dia = new Integer(referencia.substring(4, 6))
								.intValue();
						fechaArchivo.set(Calendar.YEAR, anio);
						fechaArchivo.set(Calendar.MONTH, (mes - 1));
						fechaArchivo.set(Calendar.DATE, dia);
						if (fechaArchivo.before(fechaProperties)) {
							ejecutaGMC = false;
						}
					} else {
						ejecutaGMC = false;
					}
				}
			}
		}
		return ejecutaGMC;
	}

	private String obtenerHora() {
		String horaCon, hora, min, seg;
		Calendar c1 = Calendar.getInstance();
		hora = Integer.toString(c1.get(Calendar.HOUR_OF_DAY));
		min = Integer.toString(c1.get(Calendar.MINUTE));
		seg = Integer.toString(c1.get(Calendar.SECOND));
		if (hora.length() < 2) {
			hora = "0".concat(hora);
		}
		if (min.length() < 2) {
			min = "0".concat(min);
		}
		if (seg.length() < 2) {
			seg = "0".concat(seg);
		}

		horaCon = hora.concat(":").concat(min).concat(":").concat(seg);

		return horaCon;
	}

	private void crearBit(String horaInicio, String aplicativo,
			String operacion, int numXML, int numPDF, int numErr,
			String tipoDoc, String viaPDF) {
		String nuevalinea = System.getProperty("line.separator");
		GetXML getXML = new GetXML();
		CallQuery CALLQ = getXML.loadProperties(PATHPROPERTIES);
		BufferedWriter bw = null;
		String dia, mes, anio, horaCon;
		Calendar c1 = Calendar.getInstance();
		String fechaCon = "";
		String rutaFile = CALLQ.getRutaBitacora();

		File fileBit = new File(rutaFile.concat(".txt"));

		try {
			if (!fileBit.exists()) {
				fileBit = new File(rutaFile.concat(".txt"));
				bw = new BufferedWriter(new FileWriter(fileBit, true));
				// bw.write("");
			}
			bw = new BufferedWriter(new FileWriter(fileBit, true));

			dia = Integer.toString(c1.get(Calendar.DAY_OF_MONTH));
			mes = Integer.toString(c1.get(Calendar.MONTH) + 1);
			anio = Integer.toString(c1.get(Calendar.YEAR));

			if (dia.length() < 2) {
				dia = "0".concat(dia);
			}
			if (mes.length() < 2) {
				mes = "0".concat(mes);
			}

			fechaCon = dia.concat("-").concat(mes).concat("-").concat(anio);
			horaCon = obtenerHora();
			System.out.println(fechaCon);
			//
			int numTot = numXML + numPDF;
			bw.write("Fecha:" + fechaCon + "|" + "HoraInicio:" + horaInicio
					+ "|HoraFin:" + horaCon + "|Aplicativo:" + aplicativo
					+ "|Operacion:" + operacion + "|XML:" + numXML
					+ "|PDFExitosos:" + numPDF + "|Errores:" + numErr
					+ "|TotalArchivos:" + numTot + "|TipDocumento:" + tipoDoc
					+ "|ViaPDF:" + viaPDF + nuevalinea);
			bw.flush();

		} catch (Exception e) {
			try {
				bw.write("Error en el proceso");
			} catch (Exception a) {

			}
		}
	}

	// Envio de correo electrónico con documentos RUC
	public void envioMailRUC(String[] listPathFiles, String destinatario,
			String nombreCliente, String pass, String producto,
			StringHolder envioCorreo, StringHolder EXIT_STATUS,
			StringHolder ERR_DESC) {

	
			envioCorreo.value = "";
			EXIT_STATUS.value = "1";
			ERR_DESC.value = "El servicio envioMailRUC no disponible";


	}

	private String validaDatosEntradaProCFDI(String canal, String version,
			String rfcEmisor, String tipoProceso, Elemento[] contenido) {
		String resp = "";
		if (canal != null && !canal.isEmpty()) {
			if (version != null && !version.isEmpty()) {
				if (rfcEmisor != null && !rfcEmisor.isEmpty()) {
					if (tipoProceso != null && !tipoProceso.isEmpty()) {
						if (contenido != null && contenido.length > 0) {
							resp = "";
						} else {
							resp = "El contenido no puede ser nulo o vacio.";
						}
					} else {
						resp = "El tipo de proceso no puede ser nulo o vacio.";
					}
				} else {
					resp = "El rfcEmisor no puede ser nulo o vacio.";
				}
			} else {
				resp = "La version no puede ser nula o vacia.";
			}
		} else {
			resp = "El nombre del canal no puede ser nulo o vacio.";
		}
		return resp;
	}

	public void procesaCFDI(String canal, String version, String rfcEmisor,
			String tipoProceso, Elemento[] contenido, StringHolder fiscales,
			ByteArrayHolder pdf, ByteArrayHolder xml,
			StringHolder EXIT_ESTATUS, StringHolder ERR_DESC) {
		Properties pr = new Properties();
		Properties sg = new Properties();
		HashMap<String, Object> respuestaMap = null;
		String pathGMC = "";
		String pathDwnldFile = "";
		String pathServer = "";
		String valida = validaDatosEntradaProCFDI(canal, version, rfcEmisor,
				tipoProceso, contenido);
		if (valida.isEmpty()) {
			try {
				pr.load(new FileInputStream(PATHPROPERTIES));
				sg.load(new FileInputStream(pr.getProperty("PATH_GMC")));
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				pathGMC = pr.getProperty("PATH_GMC");
				if (contenido != null && contenido.length > 0) {
					List<Nodo> nodos = new ArrayList<Nodo>();
					for (Elemento ele : contenido) {
						Nodo nodo = new Nodo();
						nodo = getNodo(ele);
						nodos.add(nodo);
					}
					if (nodos.size() > 0) {
						SellaTimbra sellaTimbra = new SellaTimbra(
								pr.getProperty("PATH_SELLA_TIMBRA"),
								tipoProceso);
						respuestaMap = sellaTimbra.sellarTimbrar(canal,
								version, rfcEmisor, tipoProceso, nodos);
						if (tipoProceso.equals("1")) {
							System.out.println("Tipo 1");
							if (respuestaMap != null) {
								String respExitEs = (String) respuestaMap
										.get("EXIT_ESTATUS");
								System.out.println("respExitEs: " + respExitEs);
								if (respExitEs != null && !respExitEs.isEmpty()) {
									if (respExitEs.equals("0")) {
										System.out
												.println("respExitEs validacion: "
														+ respExitEs);
										EXIT_ESTATUS.value = respExitEs;
										String resFiscales = (String) respuestaMap
												.get("FISCALES");
										System.out.println("resFiscales: "
												+ resFiscales);
										if (resFiscales != null
												&& !resFiscales.isEmpty()) {
											fiscales.value = resFiscales;
										}
										String referencia = (String) respuestaMap
												.get("REFERENCIA");
										System.out.println("referencia: "
												+ referencia);
									/*	String periodo ="";
										String rutaXML="";*/
										String periodo = (String) respuestaMap
												.get("PERIODO");
										System.out.println("periodo: "
												+ periodo);
										String rutaXML = (String) respuestaMap
												.get("XML");

										System.out.println("rutaXML: "
												+ rutaXML);
										System.out.println("pathGMC: "
												+ pathGMC);
										System.out.println("referencia: "
												+ referencia);
										System.out.println("periodo: "
												+ periodo);
										File archXML = new File(rutaXML);
										String arr[] = new String[8];
										arr[0] = pathGMC;
										arr[1] = referencia;
										arr[2] = "0";
										arr[3] = periodo;
										arr[4] = "1";
										arr[5] = "";

										System.out.println("pathGMC: "
												+ pathGMC);
										try {
											if (archXML.exists()) {
												xml.value = IOUtils
														.toByteArray(new FileInputStream(
																archXML));
											} else {
												System.out
														.println("No existe xml");
											}
											pathDwnldFile = sg
													.getProperty("PATH_LOCAL_OUT_W6");
											System.out
													.println("pathDwnldFile: "
															+ pathDwnldFile);
											pathServer = sg
													.getProperty("URL_SERVER");
											System.out.println("pathServer: "
													+ pathServer);
											HashMap mapRespuesta = CallFtp
													.ejecuta(arr);
											String respuesta = "";
											String nomArchivo = "";
											String codigoRespuesta = "";
											String respError = "";

											if (mapRespuesta != null) {
												nomArchivo = (String) mapRespuesta
														.get("nombreArchivo");
												System.out
														.println("nomArchivo: "
																+ nomArchivo);
												respuesta = (String) mapRespuesta
														.get("respuesta");
												System.out
														.println("respuesta: "
																+ respuesta);
												codigoRespuesta = (String) mapRespuesta
														.get("codigoRespuesta");
												System.out
														.println("codigoRespuesta: "
																+ codigoRespuesta);
												respError = (String) mapRespuesta
														.get("errRespuesta");
												System.out
														.println("respError: "
																+ respError);
												if (codigoRespuesta.trim()
														.equals("0")) {
													if (respuesta != null
															&& respuesta != "") {
														if (respuesta != "1") {
															String nomPathArchivo = pathDwnldFile
																	+ nomArchivo;
															if (respuesta
																	.toUpperCase()
																	.contains(
																			"CANNOT CREATE")) {

																System.out
																		.println("El archivo "
																				+ nomArchivo
																				+ " no se pudo generar.......");
															} else {
																System.out
																		.println("Respuesta Archivo: "
																				+ nomPathArchivo);
																File archivo = new File(
																		nomPathArchivo);
																archivo.canExecute();
																archivo.canRead();
																archivo.canWrite();
																if (archivo
																		.exists()
																		|| archivo
																				.length() > 0L) {
																	pdf.value = IOUtils
																			.toByteArray(new FileInputStream(
																					archivo));
																} else {
																	System.out
																			.println("El archivo no existe o el archivo se genero vacio.");
																}
															}
														} else {
															System.out
																	.println("No se encontro documento con esos criterios.");
														}
													} else {
														System.out
																.println("Error al generar el documento respuesta vacia.");
													}
												} else {
													System.out
															.println("Error al generar el documento respuesta vacia.");
												}
											} else {
												System.out
														.println("Error al generar el documento respuesta vacia.");
											}
										} catch (Exception ex) {
											ex.printStackTrace();
										}
									} else if (respExitEs.equals("1")) {
										System.out
												.println("respExitEs validacions: "
														+ respExitEs);
										EXIT_ESTATUS.value = respExitEs;
										String err_des = (String) respuestaMap
												.get("ERR_DESC");
										System.out.println("err_des: "
												+ err_des);
										ERR_DESC.value = err_des;
									} else {
										EXIT_ESTATUS.value = "1";
										ERR_DESC.value = "Error no controlado";
									}
								} else {
									EXIT_ESTATUS.value = "1";
									ERR_DESC.value = "Respuesta vacia de exit estatus";
								}
							} else {
								EXIT_ESTATUS.value = "1";
								ERR_DESC.value = "Respuesta vacia";
							}
						} else if (tipoProceso.equals("2")) {
							System.out.println("Tipo 2");
							if (respuestaMap != null) {
								String respExitEs = (String) respuestaMap
										.get("EXIT_ESTATUS");
								System.out.println("respExitEs: " + respExitEs);
								if (respExitEs != null && !respExitEs.isEmpty()) {
									if (respExitEs.equals("0")) {
										System.out
												.println("respExitEs validacion: "
														+ respExitEs);
										EXIT_ESTATUS.value = respExitEs;
										String archivoPDF = (String) respuestaMap
												.get("ARCHIVO");
										System.out.println("archivoPDF: "
												+ archivoPDF);
										File archivo = new File(archivoPDF);
										archivo.canExecute();
										archivo.canRead();
										archivo.canWrite();
										if (archivo.exists()
												|| archivo.length() > 0L) {
											pdf.value = IOUtils
													.toByteArray(new FileInputStream(
															archivo));
											EXIT_ESTATUS.value = "0";
										} else {
											System.out
													.println("El archivo no existe o el archivo se genero vacio.");
											EXIT_ESTATUS.value = "1";
											ERR_DESC.value = "El archivo no existe o el archivo se genero vacio.";
										}
									} else {
										System.out
												.println("respExitEs validacions: "
														+ respExitEs);
										EXIT_ESTATUS.value = respExitEs;
										String err_des = (String) respuestaMap
												.get("ERR_DESC");
										System.out.println("err_des: "
												+ err_des);
										ERR_DESC.value = err_des;
									}
								} else {
									EXIT_ESTATUS.value = "1";
									ERR_DESC.value = "El archivo no existe o el archivo se genero vacio.";
								}
							} else {
								EXIT_ESTATUS.value = "1";
								ERR_DESC.value = "Mapa de respuesta vacio.";
							}
						} else if (tipoProceso.equals("3")) {
							System.out.println("Tipo 3");
							if (respuestaMap != null) {
								String respExitEs = (String) respuestaMap
										.get("EXIT_ESTATUS");
								System.out.println("respExitEs: " + respExitEs);
								if (respExitEs != null && !respExitEs.isEmpty()) {
									if (respExitEs.equals("0")) {
										System.out
												.println("respExitEs validacion: "
														+ respExitEs);
										EXIT_ESTATUS.value = respExitEs;
										String archivoPDF = (String) respuestaMap
												.get("ARCHIVO");
										System.out.println("archivoPDF: "
												+ archivoPDF);
										File archivo = new File(archivoPDF);
										archivo.canExecute();
										archivo.canRead();
										archivo.canWrite();
										if (archivo.exists()
												|| archivo.length() > 0L) {
											pdf.value = IOUtils
													.toByteArray(new FileInputStream(
															archivo));
											EXIT_ESTATUS.value = "0";
										} else {
											System.out
													.println("El archivo no existe o el archivo se genero vacio.");
											EXIT_ESTATUS.value = "1";
											ERR_DESC.value = "El archivo no existe o el archivo se genero vacio.";
										}
									} else {
										System.out
												.println("respExitEs validacions: "
														+ respExitEs);
										EXIT_ESTATUS.value = respExitEs;
										String err_des = (String) respuestaMap
												.get("ERR_DESC");
										System.out.println("err_des: "
												+ err_des);
										ERR_DESC.value = err_des;
									}
								} else {
									EXIT_ESTATUS.value = "1";
									ERR_DESC.value = "El archivo no existe o el archivo se genero vacio.";
								}
							} else {
								EXIT_ESTATUS.value = "1";
								ERR_DESC.value = "Mapa de respuesta vacio.";
							}
						} else {
							EXIT_ESTATUS.value = "1";
							ERR_DESC.value = "Tipo de proceso no identificado";
						}

					} else {
						EXIT_ESTATUS.value = "1";
						ERR_DESC.value = "Nodos vacios";
					}
				} else {
					EXIT_ESTATUS.value = "1";
					ERR_DESC.value = "El contenido es vacio";

				}
			} catch (Exception ex) {
				ex.printStackTrace();
				EXIT_ESTATUS.value = "1";
				ERR_DESC.value = "Exception: " + ex.getMessage();
			}
		} else {
			EXIT_ESTATUS.value = "1";
			ERR_DESC.value = valida;
		}
	}

	private Nodo getNodo(Elemento elemento) {
		Nodo nodo = new Nodo();
		nodo.setNombre(elemento.getNombre());
		if (elemento.getElementos() != null
				&& elemento.getElementos().length > 0) {
			List<Nodo> nodos = new ArrayList<Nodo>();
			for (Elemento element : elemento.getElementos()) {
				Nodo no = getNodo(element);
				nodos.add(no);
			}
			nodo.setNodos(nodos);
		}
		if (elemento.getDatos() != null && elemento.getDatos().length > 0) {
			List<Atributo> atributos = new ArrayList<Atributo>();
			for (Dato dato : elemento.getDatos()) {
				Atributo atr = new Atributo();
				atr.setNombre(dato.getNombreDato());
				atr.setValor(dato.getValor());
				atributos.add(atr);
			}
			nodo.setAtributos(atributos);
		}
		return nodo;
	}

	private String cargaFechaNomArchivo() {
		Calendar fechaHoy = Calendar.getInstance();
		String respuesta = "";
		String sMes = "";
		String sDia = "";
		int num1 = 97;
		int num2 = 122;
		Random random = new Random();
		Calendar cal = Calendar.getInstance();
		try {
			int anio = (1900 + fechaHoy.get(Calendar.YEAR)) % 100;
			int mes = (fechaHoy.get(Calendar.MONTH) + 1);
			int dia = fechaHoy.get(Calendar.DATE);
			if (mes > 0 && mes < 10) {
				sMes = "0" + mes;
			} else {
				sMes = "" + mes;
			}
			if (dia > 0 && dia < 10) {
				sDia = "0" + dia;
			} else {
				sDia = "" + dia;
			}
			respuesta = anio + sMes + sDia;
			int numAleatorio = (int) Math.floor(Math.random() * (num2 - num1)
					+ num1);
			System.out.println("numAleatorio: " + numAleatorio);
			int randomNumber = random.nextInt(99999);
			Formatter fmt = new Formatter();
			String resto = fmt.format("%08d", randomNumber).toString();
			System.out.println("resto: " + resto);
			String letra = "" + (char) numAleatorio;
			System.out.println("letra: " + letra);
			letra = letra.toUpperCase();
			String tm = "" + cal.getTimeInMillis();
			respuesta = letra.concat(tm).concat(resto);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return respuesta;
	}

	private byte[] concatenatePdfs(List<File> listOfPdfFiles, File outputFile)
			throws DocumentException, IOException {
		byte[] bytesPDF = null;
		Document document = new Document();
		FileOutputStream outputStream = new FileOutputStream(outputFile);
		PdfWriter writer = PdfWriter.getInstance(document, outputStream);
		document.open();

		for (File inFile : listOfPdfFiles) {
			PdfReader reader = new PdfReader(inFile.getAbsolutePath());
			PdfContentByte cb = writer.getDirectContent();
			for (int i = 1; i <= reader.getNumberOfPages(); i++) {
				document.newPage();
				PdfImportedPage page = writer.getImportedPage(reader, i);
				cb.addTemplate(page, 0, 0);
			}
			reader.close();
		}

		outputStream.flush();
		document.close();
		outputStream.close();

		bytesPDF = readFully(new FileInputStream(outputFile));
		outputFile.delete();
		return bytesPDF;
	}

	/**
	 * getxmlSAT(String serie, String folio, String fEmision, String CUAE,
	 * String opcion, StringHolder EXIT_ESTATUS) Sin Adenda
	 * 
	 * @param serie
	 * @param folio
	 * @param fEmision
	 * @param CUAE
	 * @param opcion
	 * @param EXIT_ESTATUS
	 * @return
	 */
	public String getxmlSAT(String serie, String folio, String fEmision,
			String CUAE, String opcion, StringHolder EXIT_ESTATUS) {
		System.out.println("RFC executeInvoke, Antes: " + serie);
		serie = RFCamp(serie);
		System.out.println("RFC executeInvoke, Despues: " + serie);
		EXIT_ESTATUS.value = "";
		Boolean CFDI = Boolean.valueOf(false);
		String rValidaicon = "";
		if ((opcion.equals("0")) || (opcion.equals("1"))) {
			opcion = traigoopcion(opcion);
			rValidaicon = setArgumentos(serie, folio, fEmision, CUAE, opcion);
		} else {
			folio = folio.toUpperCase();
			int lenFolio = folio.length();
			System.out.println(folio + "len:" + lenFolio + " hay -"
					+ folio.contains("-"));
			if ((folio.contains("-")) && (lenFolio == 36)) {
				rValidaicon = setArgumentosCFDI(serie, folio, fEmision, CUAE,
						opcion);
				CFDI = Boolean.valueOf(true);
			} else {
				rValidaicon = setArgumentos(serie, folio, fEmision, CUAE,
						opcion);
				CFDI = Boolean.valueOf(false);
			}
		}
		System.out.println(rValidaicon);
		if (rValidaicon.trim().equals("")) {
			GetXML getXML = new GetXML();
			if ((opcion.equals("0")) || (opcion.equals("1"))) {

				System.out.println("opcion:" + opcion);
			} else {
				System.out.println("antes CFDI:" + CFDI + " validacion:"
						+ rValidaicon);
				if (!CFDI.booleanValue())
					rValidaicon = validaDatos(serie, folio, fEmision, CUAE,
							opcion, CFDI);
				if (CFDI.booleanValue())
					rValidaicon = validaDatosCFDI(serie, folio, fEmision, CUAE,
							opcion, CFDI);
				System.out.println("despues CFDI:" + CFDI + " validacion:"
						+ rValidaicon);
			}
			if (rValidaicon.trim().equals("")) {

				CallQuery CALLQ = getXML.loadProperties(PATHPROPERTIES);// /llamada

				// a al
				// properties

				// ************************************************************
				Connection conFAC = null;
				try {
					conFAC = GetDataSource(CALLQ).getConnection();
					rValidaicon = getExisteXML(serie, folio, fEmision, opcion,
							CFDI, CALLQ, getXML, conFAC);
					// rValidaicon = getExisteXML(serie, folio, fEmision,
					// opcion,
					// CFDI, CALLQ, getXML);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (conFAC != null) {
						try {
							conFAC.close();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				}

				// *************************************************************

				System.out.println("rvalidation: " + rValidaicon);
				if (rValidaicon.trim().equals("1")) {
					String[] Datos = getCd_XML(serie, folio, fEmision, opcion,
							CFDI, CALLQ, getXML);
					String CD_XML = Datos[0];
					System.out.println(CD_XML);
					String valorPrivado = Datos[1];
					if ((!CD_XML.trim().equals(""))
							&& (!valorPrivado.trim().equals(""))) {
						rValidaicon = validateCUEA(serie, folio, fEmision,
								valorPrivado, CUAE, CFDI);
						if (rValidaicon.trim().equals("")) {
							EXIT_ESTATUS.value = "1";
							return getXML.executeInvokeSinAdenda(CD_XML, CALLQ);
						} else {
							EXIT_ESTATUS.value = "0";
						}
						return rValidaicon;
					}
					EXIT_ESTATUS.value = "0";
					return "No se encontro ningún Comprobante Fiscal Digital con los parametros proporcionados(2).";
				}
				EXIT_ESTATUS.value = "0";
				return "No se encontro ningún Comprobante Fiscal Digital con los parametros proporcionados(1)";
			}
			EXIT_ESTATUS.value = "0";
			return rValidaicon;
		}
		EXIT_ESTATUS.value = "0";
		return rValidaicon;
	}
	



	public String[] cancelaCFDI(String tipoProceso, Elemento[] contenido,
			StringHolder fiscales, StringHolder EXIT_ESTATUS,
			StringHolder ERR_DESC) {
		String[] respuesta=null;

			EXIT_ESTATUS.value = "1";
			ERR_DESC.value = "El servicio cancelaCFDI no disponible ";
		
		return respuesta;
	}

	private List<Comprobante> leeArchivoResCancelacion(String res)
			throws Exception {
		List<Comprobante> comprobantes = new ArrayList<Comprobante>();
		int iniRes = res.indexOf("Cancelaciones:");
		if (iniRes != -1) {
			String sRes = res.substring(iniRes, res.lastIndexOf("|"));
			if (sRes != null && !sRes.isEmpty()) {
				String[] resTot = sRes.split("@");
				if (resTot != null && resTot.length > 0) {
					for (String sComp : resTot) {
						String[] resComp = sComp.split("//|");
						if (resComp != null && resComp.length > 0) {
							comprobantes.add(new Comprobante(resComp[2],
									resComp[0], resComp[1], resComp[3], "",
									new Integer(resComp[4].trim()), resComp[5],
									new Integer(resComp[6].trim()), resComp[7],
									resComp[8], "", "", ""));
						}
					}
				}
			}

		}
		return comprobantes;
	}

	private String generaArchivoCancelacion(List<Comprobante> lista)
			throws Exception {
		String respuesta = "";
		String separador = "|";
		String separa = "";
		for (Comprobante comprobante : lista) {
			respuesta = separa.concat(separador)
					.concat(comprobante.getRfcEmisor()).concat(separador)
					.concat(comprobante.getRfcReceptor()).concat(separador)
					.concat(comprobante.getUuid()).concat(separador)
					.concat(comprobante.getFechaEmision()).concat(separador)
					.concat("\n");
			separa = "@";
		}
		respuesta = "\"".concat(respuesta).concat("\"");
		return respuesta;
	}

	private String generaCodigoCDXML() {
		String respuesta = "";
		// System.out.println("generaCodigoCDXML");
		int num1 = 97;
		int num2 = 122;
		Calendar cal = Calendar.getInstance();
		Random random = new Random();
		try {
			int numAleatorio = (int) Math.floor(Math.random() * (num2 - num1)
					+ num1);
			// System.out.println("numAleatorio: "+numAleatorio);
			int randomNumber = random.nextInt(99999);
			// System.out.println("randomNumber: "+randomNumber);
			Formatter fmt = new Formatter();
			String resto = fmt.format("%08d", randomNumber).toString();
			// System.out.println("resto: "+resto);
			String letra = "" + (char) numAleatorio;
			// System.out.println("letra: "+letra);
			letra = letra.toUpperCase();
			String tm = "" + cal.getTimeInMillis();
			respuesta = letra.concat(tm).concat(resto);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return respuesta;
	}
	
	
	/*public void uploadFile(byte[] file) {

	        try{ 
	        
	        InputStream input = new ByteArrayInputStream(file);
	                OutputStream output = new FileOutputStream(
	                        new File("C:/test.jpg".toString()));
	            
	            byte[] b = new byte[100000];
	            int bytesRead = 0;
	            while ((bytesRead = input.read(b)) != -1) {
	                output.write(b, 0, bytesRead);
	            }
	            input.close();
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }

	    }*/

	/*
	 * public static void main(String[] args) throws IOException {
	 * 
	 * WSGetCFD servicio = new WSGetCFD();
	 * 
	 * ByteArrayHolder xml = new ByteArrayHolder();
	 * 
	 * xml.value = IOUtils .toByteArray(
	 * "<?xml version=\"1.0\" encoding=\"UTF-8\"?><cfdi:Comprobante xmlns:pago10=\"http://www.sat.gob.mx/Pagos\" xsi:schemaLocation=\"http://www.sat.gob.mx/cfd/3 http://www.sat.gob.mx/sitio_internet/cfd/3/cfdv33.xsd http://www.sat.gob.mx/Pagos http://www.sat.gob.mx/sitio_internet/cfd/Pagos/Pagos10.xsd\" Version=\"3.3\" Serie=\"1\" Folio=\"20355\" Fecha=\"2018-12-29T10:31:40\" Sello=\"eQWadc6iRNZY9ZCgoroWOkwBoVqHEZERqcmyVwXFjHfwUfcXUmSqV67+VXg5OZMcYbIdPz2NvvTc2eXPJFQZSFgKjDuxXfQawpKlQ+ShTo9pEd5h7fCcd4gWiepZR+TvD0OyAVZlpon6VTCYrvo4gYb/MnwZlQrg+8GS+IxWXzSQwF0HnMEgq/GvHAywIB6WI17f1wfOsR0tN4Ij+7KH9n9qv5y3DO2ete5DfV/az8bwaYK3vrs/U9EQ5eKit1yg6ise+6oHxZ1ou6WHwpG5NEXu4TsytA7cl7c5MAvTZwUcjMKyvWSHvo8uyaPs3r/xrOeta6KsghYcOqTkVHIEPg==\" NoCertificado=\"00001000000404419165\" Certificado=\"MIIGEjCCA/qgAwIBAgIUMDAwMDEwMDAwMDA0MDQ0MTkxNjUwDQYJKoZIhvcNAQELBQAwggGyMTgwNgYDVQQDDC9BLkMuIGRlbCBTZXJ2aWNpbyBkZSBBZG1pbmlzdHJhY2nDs24gVHJpYnV0YXJpYTEvMC0GA1UECgwmU2VydmljaW8gZGUgQWRtaW5pc3RyYWNpw7NuIFRyaWJ1dGFyaWExODA2BgNVBAsML0FkbWluaXN0cmFjacOzbiBkZSBTZWd1cmlkYWQgZGUgbGEgSW5mb3JtYWNpw7NuMR8wHQYJKoZIhvcNAQkBFhBhY29kc0BzYXQuZ29iLm14MSYwJAYDVQQJDB1Bdi4gSGlkYWxnbyA3NywgQ29sLiBHdWVycmVybzEOMAwGA1UEEQwFMDYzMDAxCzAJBgNVBAYTAk1YMRkwFwYDVQQIDBBEaXN0cml0byBGZWRlcmFsMRQwEgYDVQQHDAtDdWF1aHTDqW1vYzEVMBMGA1UELRMMU0FUOTcwNzAxTk4zMV0wWwYJKoZIhvcNAQkCDE5SZXNwb25zYWJsZTogQWRtaW5pc3RyYWNpw7NuIENlbnRyYWwgZGUgU2VydmljaW9zIFRyaWJ1dGFyaW9zIGFsIENvbnRyaWJ1eWVudGUwHhcNMTYxMjAxMTY1OTM1WhcNMjAxMjAxMTY1OTM1WjCBsjEYMBYGA1UEAxMPQUZMRU1TIFNBIERFIENWMRgwFgYDVQQpEw9BRkxFTVMgU0EgREUgQ1YxGDAWBgNVBAoTD0FGTEVNUyBTQSBERSBDVjElMCMGA1UELRMcQUZMOTkwMTA5Q1Y1IC8gRk9DUjY4MDUyNTE3OTEeMBwGA1UEBRMVIC8gRk9DUjY4MDUyNUhOTExWTTAzMRswGQYDVQQLExJTRUxMT1MgQUZMRU1TIDIwMTYwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQCMm7FjZOF+kpv6OY47N4r7E6JD4M/GWjaubafh/jRRkL5HvRoLAJw2LO8FBd1OaFdIsYX1coXlCXsmDzS0NAPvbSH6cI58nwCVFVf7TDPyjl5oXAD0i1fr1oWD7IqyKokmU3UINmIiAszoQK2BIn+h87AC3FjGSSQGlo9M3Z7v8X+Tzoh8htAs8Ac15ItoL1al2i0bvDE3lwApsokxKRI47FNtLDCe4b+EVzSlO+tObhgnYmiBqrD3IutzY4QHiyN6HcHqEXRN1Nn7j6lDQSET8+OtWp4g5qcltcoGuilzSEoSHN2kYE1ARFuTrI/w0JsJLXrSxHv0Plu1EEFZve/LAgMBAAGjHTAbMAwGA1UdEwEB/wQCMAAwCwYDVR0PBAQDAgbAMA0GCSqGSIb3DQEBCwUAA4ICAQAoOQqhvl82Xgr52IcXDbAsJmXhVH4l6CWXJYuz+H235bUq+myGhwvakNp84L+XyjvASrgf7jTJeH3ZNuHNxmZWShrLqShks55f3k3VIKe7cOjXcvZcWSKXuCqEjXBHkgvEIy3v0kpkohPtT4uZTTaWydTd198MvUdCpLg/yqZhmN6F4UmEGt9BrvQnH4qmeKQW+ZtRUJAU8vzWxkRJRbjgdDCPiKj9S/qV1eGO/sQDxgFmjhnpfezAvHAtFh3nudClDIlxuwhZ6OrpkkTQsicLpbGOZZPiA7WhMe5SRSv+tq4rT6MDZEPpzzKReWIoRDGjYER2EmXXBtB0/rpHMgR9s7krGIbwZD7N0mB/MoMzfxxo0Oxqb8EAYOe6VYk5L9e1VCFA21Op4ucVeS+eBXStPyxOestxUeHtJWOx+2C6vMprH+cPQqiCjdaGJnrH6kh7FCu1up6yxHjaEHjgI6AcMQcM3L2rIhSw4kq4+0Qy+D8N9iFMoEUOQF1YZ8+uk5JSLUcLfVlucj9QaReu/2SgWhTZK09mfuD3FSmVv6OsDNDsu8IaGugpxyL+OgTJc8RTTV4YFKb2mDwyt5jfnZgliKgIbkaWTYK2hic/to2JwQGcyXWvICnQhPMXsddcaFcz1izqRq+fUeagmtsjIWPlVhZScHv433pZeBOO4e52Bw==\" SubTotal=\"0\" Moneda=\"XXX\" Total=\"0\" TipoDeComprobante=\"P\" LugarExpedicion=\"67350\" xmlns:cfdi=\"http://www.sat.gob.mx/cfd/3\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><cfdi:Emisor Rfc=\"AFL990109CV5\" Nombre=\"AFLEMS SA DE CV\" RegimenFiscal=\"624\" /><cfdi:Receptor Rfc=\"BBA830831LJ2\" Nombre=\"BBVA BANCOMER S.A. INSTITUCION DE BANCA MULTIPLE GRUPO FINANCIERO BBVA BANCOMER\" UsoCFDI=\"P01\" /><cfdi:Conceptos><cfdi:Concepto ClaveProdServ=\"84111506\" Cantidad=\"1\" ClaveUnidad=\"ACT\" Descripcion=\"Pago\" ValorUnitario=\"0\" Importe=\"0\" /></cfdi:Conceptos><cfdi:Complemento><tfd:TimbreFiscalDigital Version=\"1.1\" UUID=\"548AAA70-ACFD-4E82-B508-9B8B63A34946\" FechaTimbrado=\"2018-12-29T10:36:45\" RfcProvCertif=\"BUZ021107UG0\" SelloCFD=\"eQWadc6iRNZY9ZCgoroWOkwBoVqHEZERqcmyVwXFjHfwUfcXUmSqV67+VXg5OZMcYbIdPz2NvvTc2eXPJFQZSFgKjDuxXfQawpKlQ+ShTo9pEd5h7fCcd4gWiepZR+TvD0OyAVZlpon6VTCYrvo4gYb/MnwZlQrg+8GS+IxWXzSQwF0HnMEgq/GvHAywIB6WI17f1wfOsR0tN4Ij+7KH9n9qv5y3DO2ete5DfV/az8bwaYK3vrs/U9EQ5eKit1yg6ise+6oHxZ1ou6WHwpG5NEXu4TsytA7cl7c5MAvTZwUcjMKyvWSHvo8uyaPs3r/xrOeta6KsghYcOqTkVHIEPg==\" NoCertificadoSAT=\"00001000000404347791\" SelloSAT=\"S1F9lRwmMla4ahG1Q73/Y7N6GcSUVo/w3rMnUDJbqXuhjhrEPsJzdi0tkK/OOAtC5dQ7gEMx3+0AJCGh3pVfVJ4XxNmIQTzftvlIwjeEQJdseSRmPzlWExipckrpnwvnZXuAtOaWwgemwvjnCR+nIrxvXPFu2BhppA5dQM6LbqRXxclxoH2o3sz8sGcTT36Gh71xiLh1e206urShu9sJ4a7vtrjzUPwVUgRp9R8/NMeLdjiNpWjCK74bjxNoz9aOt4clk+LCNF2ELnumcnlAdUnputunx/5O6plumK6HUVxhNTrvif+zPei03l7z5/uUzoAxb7UMdA8CSxxhb6xmvA==\" xsi:schemaLocation=\"http://www.sat.gob.mx/TimbreFiscalDigital http://www.sat.gob.mx/sitio_internet/cfd/TimbreFiscalDigital/TimbreFiscalDigitalv11.xsd\" xmlns:tfd=\"http://www.sat.gob.mx/TimbreFiscalDigital\" /><pago10:Pagos Version=\"1.0\"><pago10:Pago FechaPago=\"2018-12-28T12:00:00\" FormaDePagoP=\"03\" MonedaP=\"MXN\" Monto=\"966446.72\" CtaOrdenante=\"000000000000000000\" CtaBeneficiario=\"012597004537436592\"><pago10:DoctoRelacionado IdDocumento=\"1307F3B2-D3EE-4CBA-99FB-FED640D65DF8\" Serie=\"NCA\" Folio=\"58424\" MonedaDR=\"MXN\" MetodoDePagoDR=\"PPD\" NumParcialidad=\"1\" ImpSaldoAnt=\"966446.72\" ImpPagado=\"966446.72\" ImpSaldoInsoluto=\"0.00\" /></pago10:Pago></pago10:Pagos></cfdi:Complemento></cfdi:Comprobante>"
	 * );
	 * 
	 * servicio.validacionSat(xml, null, null, null, null);
	 * 
	 * }
	 */

	/*
	 * public static void main(String[] args) throws NoClassDefFoundError,
	 * SQLException, Exception {
	 * 
	 * Connection conFAC = null; Connection conEDC = null;
	 * 
	 * HashMap mapRespuesta = null;
	 * 
	 * String codigoRespuesta = "";
	 * 
	 * String[] argumentos = new String[6];
	 * 
	 * argumentos[0] = "C://export//GeneratePDFWithGMC.properties";
	 * argumentos[1] = "180212HIPCONXXXX0000000000000000008029"; argumentos[2] =
	 * "0"; argumentos[3] = "201802"; argumentos[4] = "1"; argumentos[5] = "";
	 * 
	 * Properties prop = new Properties(); prop.load(new FileInputStream(
	 * "C://export//GeneratePDFWithGMC.properties"));
	 * 
	 * conEDC = dao.ConnectionBD.getConnectionBD(prop.getProperty("PATH_BD"));
	 * conFAC = dao.ConnectionBD.getConnectionBD(prop
	 * .getProperty("PATH_BD_FAC"));
	 * 
	 * Ejecuta pdfeje = new Ejecuta();
	 * 
	 * mapRespuesta = pdfeje.ejecuta(argumentos, conEDC, conFAC);
	 * 
	 * conEDC.close(); conFAC.close();
	 * 
	 * codigoRespuesta = (String) mapRespuesta.get("codigoRespuesta");
	 * 
	 * System.out.println("CR: " + codigoRespuesta);
	 * 
	 * }
	 */

	/*
	 * public static void main(String[] args) throws NoClassDefFoundError,
	 * SQLException, Exception {
	 * 
	 * WSGetCFD servicio = new WSGetCFD();
	 * 
	 * byte[] byteArray;
	 * 
	 * byteArray = servicio.getZipFac("", "", "", "", "", "", "", "1",
	 * "180212HIPCONXXXX0000000000000000008028,201802", "", "", null);
	 * 
	 * System.out.println(DatatypeConverter.printBase64Binary(byteArray));
	 * 
	 * }
	 */

}
