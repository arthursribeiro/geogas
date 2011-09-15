package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class index_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.AnnotationProcessor _jsp_annotationprocessor;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_annotationprocessor = (org.apache.AnnotationProcessor) getServletConfig().getServletContext().getAttribute(org.apache.AnnotationProcessor.class.getName());
  }

  public void _jspDestroy() {
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html; charset=ISO-8859-1");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("\r\n");
      out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\">\r\n");
      out.write("<title>GeoGas</title>\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("</script>\r\n");
      out.write("</head>\r\n");
      out.write("<body >\r\n");
      out.write("Welcome to GeoGas!\r\n");
      out.write("\r\n");
      out.write("<form id='frmAberto' name='frmAberto' action=\"http://www.anp.gov.br/preco/prc/Resumo_Por_Estado_Municipio.asp\">\r\n");
      out.write("\r\n");
      out.write("<input type=\"hidden\" name=\"selSemana\" value=\"626*De 12/06/2011 a 18/06/2011\">\r\n");
      out.write("\r\n");
      out.write("<input type=hidden name=desc_Semana value='de 12/06/2011 a 18/06/2011'>\r\n");
      out.write("\r\n");
      out.write("<input type=hidden name=cod_Semana value='626'>\r\n");
      out.write("\r\n");
      out.write("<input type=hidden name=tipo value='1'> \r\n");
      out.write("\r\n");
      out.write("<input type=hidden name=Cod_Combustivel value=\"\">    \r\n");
      out.write("\t\r\n");
      out.write("      <select name=selEstado style='width:150px;'><option selected value='AC*ACRE'>Acre</option><option value='AL*ALAGOAS'>Alagoas</option><option value='AP*AMAPÁ'>Amapá</option><option value='AM*AMAZONAS'>Amazonas</option><option value='BA*BAHIA'>Bahia</option><option value='CE*CEARÁ'>Ceará</option><option value='DF*DISTRITO@FEDERAL'>Distrito Federal</option><option value='ES*ESPÍRITO@SANTO'>Espírito Santo</option><option value='GO*GOIÁS'>Goiás</option><option value='MA*MARANHÃO'>Maranhão</option><option value='MT*MATO@GROSSO'>Mato Grosso</option><option value='MS*MATO@GROSSO@DO@SUL'>Mato Grosso do Sul</option><option value='MG*MINAS@GERAIS'>Minas Gerais</option><option value='PR*PARANÁ'>Paraná</option><option value='PB*PARAÍBA'>Paraíba</option><option value='PA*PARÁ'>Pará</option><option value='PE*PERNAMBUCO'>Pernambuco</option><option value='PI*PIAUÍ'>Piauí</option><option value='RJ*RIO@DE@JANEIRO'>Rio de Janeiro</option><option value='RN*RIO@GRANDE@DO@NORTE'>Rio Grande do Norte</option><option value='RS*RIO@GRANDE@DO@SUL'>Rio Grande do Sul</option><option value='RO*RONDÔNIA'>Rondônia</option><option value='RR*RORAIMA'>Roraima</option><option value='SC*SANTA@CATARINA'>Santa Catarina</option><option value='SE*SERGIPE'>Sergipe</option><option value='SP*SÃO@PAULO'>São Paulo</option><option value='TO*TOCANTINS'>Tocantins</option></select>\r\n");
      out.write("\t\t<select name=selCombustivel style='width:150px;'><option selected value='487*Gasolina'>Gasolina</option><option value='643*Etanol'>Etanol</option><option value='476*GNV'>GNV</option><option value='532*Diesel'>Diesel</option><option value='462*GLP'>GLP</option></select>   \r\n");
      out.write("\t\t\t\t<button id=image1 name=image1 type=\"submit\" >Processar</button>\r\n");
      out.write("</form>\r\n");
      out.write("\r\n");
      out.write("</body>\r\n");
      out.write("</html>");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
