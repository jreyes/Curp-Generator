package curp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CurpGeneratorGUI
    extends JPanel
{
// ------------------------------ FIELDS ------------------------------

    private static final String[] DAYS = {"01",
                                          "02",
                                          "03",
                                          "04",
                                          "05",
                                          "06",
                                          "07",
                                          "08",
                                          "09",
                                          "10",
                                          "11",
                                          "12",
                                          "13",
                                          "14",
                                          "15",
                                          "16",
                                          "17",
                                          "18",
                                          "19",
                                          "20",
                                          "21",
                                          "22",
                                          "23",
                                          "24",
                                          "25",
                                          "26",
                                          "27",
                                          "28",
                                          "29",
                                          "30",
                                          "31"};

    private static SimpleDateFormat formatter = new SimpleDateFormat( "MM/dd/yyyy" );

    private JTextField año;

    private JComboBox dia;

    private JComboBox entidadFederativa;

    private JComboBox mes;

    private JTextField nombres;

    private JTextField primerApellido;

    private JLabel resultado;

    private JTextField segundoApellido;

    private ButtonGroup sexo;

    private static void createAndShowGUI()
    {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.add( new CurpGeneratorGUI() );
        frame.pack();
        frame.setVisible( true );
    }

// --------------------------- CONSTRUCTORS ---------------------------

    public CurpGeneratorGUI()
    {
        super();

        // crear el layout
        GridBagLayout layout = new GridBagLayout();
        layout.columnWidths = new int[]{130, 10, 300};
        setLayout( layout );

        // setear el JPanel
        setPreferredSize( new Dimension( 600, 400 ) );

        // agregar los componentes
        agregarResultado();
        agregarPrimerApellido();
        agregarSegundoApellido();
        agregarNombres();
        agregarSexo();
        agregarFechaDeNacimiento();
        agregarEntidadFederativa();
        agregarBotones();

        limpiar();
    }

// -------------------------- OTHER METHODS --------------------------

    private void agregarBotones()
    {
        JButton limpiar = new JButton( "Limpiar" );
        limpiar.addActionListener( new ActionListener()
        {
            @Override
            public void actionPerformed( ActionEvent e )
            {
                limpiar();
            }
        } );

        JButton generar = new JButton( "Generar" );
        generar.addActionListener( new ActionListener()
        {
            @Override
            public void actionPerformed( ActionEvent event )
            {
                generar();
            }
        } );

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 7;
        c.gridwidth = 3;

        JPanel p = new JPanel();
        p.add( limpiar );
        p.add( generar );
        add( p, c );
    }

    private void agregarEntidadFederativa()
    {
        entidadFederativa = new JComboBox( CurpGenerator.ENTIDAD_FEDERATIVA );
        entidadFederativa.setPreferredSize( new Dimension( 300, 30 ) );

        agregarFila( 6, "Entidad Federativa", entidadFederativa );
    }

    private void agregarFechaDeNacimiento()
    {
        dia = new JComboBox( DAYS );
        dia.setPreferredSize( new Dimension( 50, 30 ) );

        mes = new JComboBox( CurpGenerator.MESES );
        mes.setPreferredSize( new Dimension( 50, 30 ) );

        año = new JTextField();
        año.setPreferredSize( new Dimension( 50, 30 ) );

        agregarFila( 5, "Fecha de Nacimiento", dia, mes, año );
    }

    private void agregarFila( int fila, String etiqueta, Component... componentes )
    {
        JLabel l = new JLabel( etiqueta, JLabel.RIGHT );
        add( l, constraint( 0, fila ) );

        JPanel p = new JPanel( new FlowLayout( FlowLayout.LEFT ) );
        for ( Component componente : componentes )
        {
            p.add( componente );
        }
        add( p, constraint( 2, fila ) );
    }

    private void agregarNombres()
    {
        nombres = new JTextField();
        nombres.setPreferredSize( new Dimension( 300, 30 ) );

        agregarFila( 3, "Nombre(s)", nombres );
    }

    private void agregarPrimerApellido()
    {
        primerApellido = new JTextField();
        primerApellido.setPreferredSize( new Dimension( 300, 30 ) );

        agregarFila( 1, "Primer Apellido", primerApellido );
    }

    private void agregarResultado()
    {
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 3;

        resultado = new JLabel( "", JLabel.CENTER );
        resultado.setBackground( Color.BLACK );
        resultado.setFont( new Font( "Verdana", Font.BOLD, 24 ) );
        resultado.setForeground( Color.GREEN );
        resultado.setOpaque( true );
        resultado.setPreferredSize( new Dimension( 500, 40 ) );
        add( resultado, c );
    }

    private void agregarSegundoApellido()
    {
        segundoApellido = new JTextField();
        segundoApellido.setPreferredSize( new Dimension( 300, 30 ) );

        agregarFila( 2, "Segundo Apellido", segundoApellido );
    }

    private void agregarSexo()
    {
        JRadioButton hombre = new JRadioButton( "Hombre" );
        hombre.setActionCommand( "H" );

        JRadioButton mujer = new JRadioButton( "Mujer" );
        mujer.setActionCommand( "M" );

        sexo = new ButtonGroup();
        sexo.add( hombre );
        sexo.add( mujer );

        agregarFila( 4, "Sexo", hombre, mujer );
    }

    private GridBagConstraints constraint( int gridx, int gridy )
    {
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = gridx;
        c.gridy = gridy;
        return c;
    }

    private void generar()
    {
        try
        {
            String s = CurpGenerator.generar( primerApellido.getText(),
                                              segundoApellido.getText(),
                                              nombres.getText(),
                                              getSexo(),
                                              getFechaDeNacimiento(),
                                              getEntidadFederativa( entidadFederativa.getSelectedItem() ) );
            resultado.setText( s );
        }
        catch ( Exception e )
        {
            JOptionPane.showMessageDialog( this, e.getMessage(), "", JOptionPane.ERROR_MESSAGE );
        }
    }

    private String getEntidadFederativa( Object entidadFederativa )
    {
        for ( int i = 0; i < CurpGenerator.ENTIDAD_FEDERATIVA.length; i++ )
        {
            if ( CurpGenerator.ENTIDAD_FEDERATIVA[i].equals( entidadFederativa ) )
            {
                return CurpGenerator.ENTIDAD_FEDERATIVA_VALOR[i];
            }
        }
        return "";
    }

    private String getFechaDeNacimiento()
    {
        try
        {
            String a = año.getText();
            String d = String.valueOf( dia.getSelectedItem() );
            String m = "";
            for ( int i = 0; i < CurpGenerator.MESES.length; i++ )
            {
                if ( CurpGenerator.MESES[i].equals( mes.getSelectedItem() ) )
                {
                    m = CurpGenerator.MESES_VALOR[i];
                }
            }

            Date fechaDeNacimiento = formatter.parse( m + "/" + d + "/" + a );
            return formatter.format( fechaDeNacimiento );
        }
        catch ( ParseException e )
        {
            return "";
        }
    }

    private String getSexo()
    {
        if ( sexo != null && sexo.getSelection() != null )
        {
            return sexo.getSelection().getActionCommand();
        }
        return "";
    }

    private void limpiar()
    {
        resultado.setText( "" );

        primerApellido.setText( "" );
        segundoApellido.setText( "" );
        nombres.setText( "" );
        sexo.clearSelection();
        dia.setSelectedItem( null );
        mes.setSelectedItem( null );
        año.setText( "" );
        entidadFederativa.setSelectedItem( null );

        primerApellido.requestFocus();
    }

// --------------------------- main() method ---------------------------

    public static void main( String... args )
    {
        javax.swing.SwingUtilities.invokeLater( new Runnable()
        {
            public void run()
            {
                createAndShowGUI();
            }
        } );
    }
}
