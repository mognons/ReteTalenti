<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="US-ASCII"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>

    </head>
    <body>

        <h1>FORM CALCOLO INDICE DI BISOGNO</h1>
        <h2>Calcolo punteggio per graduatoria</h2>
        <s:form action="">  
            <table >
                <tbody>
                    <tr valign="top">
                        <td>
                            <table>
                                <tbody>
                                    <tr>
                                        <td>
                                            <div style=" font-size: larger"><p><s:text name="DATI RICHIESTI"/></p></div>
                                                    <s:textfield label="1. ISEE - Valore in EURO" name="isee_euro" value=""/>
                                                    <s:textfield label="2. Contributo comune per spese documentate" name="cc_euro" value=""/>
                                                    <s:textfield label="3. Contributo affitto regione per spese documentate" name="ca_euro" value=""/>
                                                    <s:textfield label="4. Contributo spot privato sociale per spese documentate" name="cs_euro" value=""/>
                                                    <s:textfield label="5. Stato di disoccupazione di lunga durata (mesi)" name="stato_disoc" value=""/>
                                                    <s:textfield label="6. Spese impreviste e straordinarie" name="spese_imp" value="" />
                                                    <s:textfield label="7. Carattere di urgenza e condizione socio-economica" name="urgenza" value=""/>
                                            </div>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </td>
                        <td>
                            <table>
                                <tbody>
                                    <tr >
                                        <td >
                                            <div style=" font-size: larger"><p><s:text name="CALCOLO PUNTEGGIO"/></p></div>
                                                    <s:textfield label="ISEE - punti p1" name="isee_punti" readonly="true" />
                                                    <s:textfield label="ENTRATE MONETARIE NON CONTEMPLATE IN ISEE - punti p2" name="entrate_nc_punti" readonly="true" />
                                                    <s:textfield label="STATO DISOCCUPAZIONE LUNGA DURATA - punti p3" name="stato_disoc_punti" readonly="true" />
                                                    <s:textfield label="SPESE IMPREVISTE E STRAORDINARIE - punti p4" name="spese_imp_punti" readonly="true" />
                                                    <s:textfield label="CARATTERE DI URGENZA - punti p5" name="urgenza_punti" readonly="true" />
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </td>
                    </tr>
                </tbody>
            </table>
                                        
                                        <br/>
            <table>
                <tbody>
                    <tr>
                        <td>
                            <table>
                                <tbody>
                                    <tr>
                                        <td><s:submit value="          Annulla          " action="listacontratti"/></td>
                                    </tr>
                                </tbody>
                            </table>
                        </td>
                        <td>
                            <table>
                                <tbody>
                                    <tr>
                                        <td> <s:submit value="          OK          "></s:submit></td>
                                        </tr>
                                    </tbody>
                                </table>
                            </td>
                        </tr>
                    </tbody>
                </table>


        </s:form>
        <!--        <script>
                    var myUsername = "";
                    $(function () {
                        $("#tabs").tabs();
                    });
                </script>-->
    </body>
</html>