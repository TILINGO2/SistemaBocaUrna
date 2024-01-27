import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class EncuestaBocaDeUrna {
    private JFrame frame;
    private JComboBox<String> ciudadComboBox;
    private ButtonGroup candidatosGroup;
    private JPanel candidatosPanel;
    private Map<String, Integer> votosPorCandidato;
    private Map<String, String[]> candidatosPorCiudad;

    private Map<String, String> propuestasPorCandidato;

    private Map<String, String> votosConCiudad;

    public EncuestaBocaDeUrna() {
        votosPorCandidato = new HashMap<>();
        candidatosPorCiudad = new HashMap<>();
        propuestasPorCandidato = new HashMap<>();

        propuestasPorCandidato.put("Sofía Gutiérrez", "Implementación de un plan integral para mejorar la red eléctrica y fluvial de la ciudad, con énfasis en la sostenibilidad y la eficiencia energética.");
        propuestasPorCandidato.put("Carlos Zambrano", "Fomento de programas de educación y capacitación tecnológica para jóvenes, con el fin de impulsar la innovación y el empleo en sectores de alta demanda.");
        propuestasPorCandidato.put("Daniela Espinoza", "Desarrollo de infraestructura verde en áreas urbanas para mejorar la calidad del aire y promover espacios públicos saludables y accesibles para todos.");

        propuestasPorCandidato.put("Mateo Vargas", "Creación de un sistema de transporte público más eficiente y ecológico, que incluya nuevas rutas de buses eléctricos y mejoras en la infraestructura de ciclovías.");
        propuestasPorCandidato.put("Isabela Martínez","Implementación de políticas de vivienda asequible para garantizar el acceso a la vivienda digna para las familias de bajos ingresos y jóvenes profesionales.");

        propuestasPorCandidato.put("Javier Andrade","Inversión en el sector salud con la construcción de nuevos centros médicos y la modernización de los existentes para mejorar la atención y los servicios de salud.");
        propuestasPorCandidato.put("Gabriela Loor","Plan de revitalización económica centrado en apoyar a las pequeñas y medianas empresas mediante incentivos fiscales y acceso a créditos con tasas preferenciales.");
        propuestasPorCandidato.put("Esteban Ochoa","Programa de seguridad ciudadana que incluya la formación de la policía comunitaria y la instalación de sistemas de vigilancia en puntos estratégicos.");
        propuestasPorCandidato.put("Lucía Peñaranda","Promoción de la cultura y el turismo local a través de la restauración de sitios históricos y la organización de festivales y eventos culturales.");

        candidatosPorCiudad.put("Ciudad: Portoviejo / Provincia de Manabí", new String[]{"Sofía Gutiérrez", "Carlos Zambrano", "Daniela Espinoza"});
        candidatosPorCiudad.put("Ciudad: Machala / Provincia de El Oro", new String[]{"Mateo Vargas", "Isabela Martínez"});
        candidatosPorCiudad.put("Ciudad: Riobamba / Provincia de Chimborazo", new String[]{"Javier Andrade", "Gabriela Loor", "Esteban Ochoa", "Lucía Peñaranda"});

        votosConCiudad = new HashMap<>();
        inicializarUI();
    }

    private void inicializarUI() {
        frame = new JFrame("Encuesta de Boca de Urna");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setSize(800, 600);

        frame.getContentPane().setBackground(new Color(255, 255, 255));
        UIManager.put("Button.font", new Font("Arial", Font.BOLD, 14));
        UIManager.put("ComboBox.font", new Font("Arial", Font.PLAIN, 14));

        String[] ciudades = {"Ciudad: Portoviejo / Provincia de Manabí", "Ciudad: Machala / Provincia de El Oro", "Ciudad: Riobamba / Provincia de Chimborazo"};
        ciudadComboBox = new JComboBox<>(ciudades);
        ciudadComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                label.setHorizontalAlignment(JLabel.CENTER);
                return label;
            }
        });

        ciudadComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarCandidatos((String) ciudadComboBox.getSelectedItem());
            }
        });

        frame.add(ciudadComboBox, BorderLayout.NORTH);

        candidatosPanel = new JPanel();
        candidatosPanel.setLayout(new BoxLayout(candidatosPanel, BoxLayout.Y_AXIS));
        candidatosGroup = new ButtonGroup();
        actualizarCandidatos(ciudades[0]);
        frame.add(candidatosPanel, BorderLayout.CENTER);

        JButton resultadosBtn = new JButton("Mostrar Resultados");
        resultadosBtn.setFont(new Font("Arial", Font.BOLD, 16));
        resultadosBtn.setBackground(new Color(32, 136, 203));
        resultadosBtn.setForeground(Color.WHITE);
        resultadosBtn.setFocusPainted(false);
        resultadosBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarResultados();
            }
        });
        frame.add(resultadosBtn, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void actualizarCandidatos(String ciudad) {
        candidatosPanel.removeAll();
        candidatosGroup = new ButtonGroup();
        String[] candidatos = candidatosPorCiudad.get(ciudad);

        for (String candidato : candidatos) {
            JRadioButton boton = new JRadioButton(candidato);
            boton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    votar(candidato);
                }
            });
            candidatosGroup.add(boton);
            candidatosPanel.add(boton);

            JTextArea propuestaArea = new JTextArea(3, 20);
            propuestaArea.setText(propuestasPorCandidato.getOrDefault(candidato, "Sin propuesta"));
            propuestaArea.setWrapStyleWord(true);
            propuestaArea.setLineWrap(true);
            propuestaArea.setEditable(false);
            propuestaArea.setBorder(BorderFactory.createTitledBorder("Propuesta de " + candidato));
            propuestaArea.setBackground(new Color(233, 236, 239));

            JScrollPane scrollPane = new JScrollPane(propuestaArea);
            scrollPane.setPreferredSize(new Dimension(200, 100));
            candidatosPanel.add(scrollPane);
        }

        candidatosPanel.revalidate();
        candidatosPanel.repaint();
    }

    private void votar(String candidato) {
        String ciudadSeleccionada = (String) ciudadComboBox.getSelectedItem();
        String claveVoto = candidato + " - " + ciudadSeleccionada;

        int votosActuales = votosConCiudad.containsKey(claveVoto) ? Integer.parseInt(votosConCiudad.get(claveVoto)) : 0;
        votosConCiudad.put(claveVoto, String.valueOf(votosActuales + 1));
    }


    private void mostrarResultados() {
        StringBuilder resultados = new StringBuilder();
        for (Map.Entry<String, String> entry : votosConCiudad.entrySet()) {
            resultados.append(entry.getKey()).append(": ").append(entry.getValue()).append(" votos\n");
        }
        JOptionPane.showMessageDialog(frame, resultados.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new EncuestaBocaDeUrna();
            }
        });
    }
}
