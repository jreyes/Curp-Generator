package curp;

import org.apache.commons.lang.StringUtils;

import javax.swing.*;
import java.util.Arrays;
import java.util.Random;

public class CurpGenerator
{
// ------------------------------ FIELDS ------------------------------

    public static final String[] MESES =
        {"Ene", "Feb", "Mar", "Abr", "May", "Jun", "Jul", "Ago", "Sep", "Oct", "Nov", "Dec"};

    public static final String[] MESES_VALOR = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};

    public static String[] ENTIDAD_FEDERATIVA = {"AGUASCALIENTES",
                                                 "BAJA CALIFORNIA NTE.",
                                                 "BAJA CALIFORNIA SUR",
                                                 "CAMPECHE",
                                                 "COAHUILA",
                                                 "COLIMA",
                                                 "CHIAPAS",
                                                 "CHIHUAHUA",
                                                 "DISTRITO FEDERAL",
                                                 "DURANGO",
                                                 "GUANAJUATO",
                                                 "GUERRERO",
                                                 "HIDALGO",
                                                 "JALISCO",
                                                 "MEXICO",
                                                 "MICHOACAN",
                                                 "MORELOS",
                                                 "NAYARIT",
                                                 "NUEVO LEON",
                                                 "OAXACA",
                                                 "PUEBLA",
                                                 "QUERETARO",
                                                 "QUINTANA ROO",
                                                 "SAN LUIS POTOSI",
                                                 "SINALOA",
                                                 "SONORA",
                                                 "TABASCO",
                                                 "TAMAULIPAS",
                                                 "TLAXCALA",
                                                 "VERACRUZ",
                                                 "YUCATAN",
                                                 "ZACATECAS",
                                                 "SERV. EXTERIOR MEXICANO",
                                                 "NACIDO EN EL EXTRANJERO"};

    public static String[] ENTIDAD_FEDERATIVA_VALOR = {"AS",
                                                       "BC",
                                                       "BS",
                                                       "CC",
                                                       "CL",
                                                       "CM",
                                                       "CS",
                                                       "CH",
                                                       "DF",
                                                       "DG",
                                                       "GT",
                                                       "GR",
                                                       "HG",
                                                       "JC",
                                                       "MC",
                                                       "MN",
                                                       "MS",
                                                       "NT",
                                                       "NL",
                                                       "OC",
                                                       "PL",
                                                       "QT",
                                                       "QR",
                                                       "SP",
                                                       "SL",
                                                       "SR",
                                                       "TC",
                                                       "TS",
                                                       "TL",
                                                       "VZ",
                                                       "YN",
                                                       "ZS",
                                                       "SM",
                                                       "NE"};


    private static char[] VOCALS = {'A', 'E', 'I', 'O', 'U'};

    private static Random random = new Random();

// -------------------------- STATIC METHODS --------------------------

    public static String generar( String primerApellido, String segundoApellido, String nombres, String sexo,
                                  String fechaDeNacimiento, String entidadDeNacimiento )
        throws Exception
    {
        // limpiar los datos
        primerApellido = trim( primerApellido );
        segundoApellido = trim( segundoApellido );
        nombres = trim( nombres );
        sexo = trim( sexo );
        entidadDeNacimiento = trim( entidadDeNacimiento );

        // validar que los datos estan correctors
        String error =
            validarDatos( primerApellido, segundoApellido, nombres, sexo, fechaDeNacimiento, entidadDeNacimiento );
        if ( error != null )
        {
            throw new Exception( error );
        }

        // generar Curp
        StringBuilder curp = new StringBuilder();
        curp.append( primerApellido.charAt( 0 ) );
        curp.append( primeraVocal( primerApellido.substring( 1 ) ) );
        curp.append( segundoApellido.charAt( 0 ) );
        curp.append( nombres.charAt( 0 ) );
        curp.append( fechaDeNacimiento.substring( 8, 10 ) );
        curp.append( fechaDeNacimiento.substring( 0, 2 ) );
        curp.append( fechaDeNacimiento.substring( 3, 5 ) );
        curp.append( sexo );
        curp.append( entidadDeNacimiento );
        curp.append( primeraLetra( primerApellido.substring( 1 ) ) );
        curp.append( primeraLetra( segundoApellido.substring( 1 ) ) );
        curp.append( primeraLetra( nombres.substring( 1 ) ) );
        curp.append( 0 );
        curp.append( random.nextInt( 10 ) );
        return curp.toString();
    }

    public static String trim( String s )
    {
        return StringUtils.trimToEmpty( s ).toUpperCase();
    }

    private static char primeraLetra( String s )
    {
        int i = StringUtils.indexOfAnyBut( s, VOCALS );
        if ( i >= 0 )
        {
            return s.charAt( i );
        }
        return 'X';
    }

    private static char primeraVocal( String s )
    {
        int i = StringUtils.indexOfAny( s, VOCALS );
        if ( i >= 0 )
        {
            return s.charAt( i );
        }
        return 'X';
    }

    private static String validarDatos( String primerApellido, String segundoApellido, String nombres, String sexo,
                                        String fechaDeNacimiento, String entidadDeNacimiento )
    {
        if ( "".equals( primerApellido ) )
        {
            return "Primer apellido es obligatorio";
        }
        if ( !StringUtils.isAlpha( primerApellido ) )
        {
            return "Primer apellido no valido, caracteres validos: A-Z (incluso Ñ)";
        }

        if ( "".equals( segundoApellido ) )
        {
            return "Segundo apellido es obligatorio";
        }
        if ( !StringUtils.isAlpha( segundoApellido ) )
        {
            return "Segundo apellido no valido, caracteres validos: A-Z (incluso Ñ)";
        }

        if ( "".equals( nombres ) )
        {
            return "Nombre(s) es obligatorio";
        }
        if ( !StringUtils.isAlphaSpace( nombres ) )
        {
            return "Nombre(s) no valido, caracteres validos: A-Z (incluso Ñ)";
        }

        if ( !"H".equals( sexo ) && !"M".equals( sexo ) )
        {
            return "Sexo no valido";
        }

        if ( "".equals( fechaDeNacimiento ) )
        {
            return "Fecha de nacimiento no valido";
        }

        if ( "".equals( entidadDeNacimiento ) )
        {
            return "Entidad de nacimiento es obligatorio";
        }
        if ( !Arrays.asList( ENTIDAD_FEDERATIVA_VALOR ).contains( entidadDeNacimiento ) )
        {
            return "Entidad de nacimiento necesita ser la abreciacion";
        }

        return null;
    }
}
