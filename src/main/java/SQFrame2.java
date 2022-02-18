import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.shortestpath.DijkstraDistance;
import edu.uci.ics.jung.algorithms.shortestpath.DijkstraShortestPath;
import edu.uci.ics.jung.graph.SparseGraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SQFrame2 extends JFrame {
    private SparseGraph g;

    private void initGraph() {
        g = new SparseGraph();
        for (int i = 1; i < 3; i++) {
            g.addVertex(i);
            /*g.addEdge("Edge[1," + (i + 1) + "]", 1, i + 1);
            if (i > 1) {
                g.addEdge("Edge[" + i + "," + (i + 1) + "]", i, i + 1, EdgeType.DIRECTED);
            }*/
            g.addEdge("ed1",1,2,EdgeType.UNDIRECTED);
            g.addEdge("ed2",2,3,EdgeType.DIRECTED);
            g.addEdge("ed3",3,1,EdgeType.DIRECTED);
        }
        System.out.println("The graph g = " + g.toString());
    }

    /*private void initGraph() {
        g = new SparseGraph();

        g.addVertex("v1");
        g.addVertex("v2");
        g.addVertex("v3");
        g.addEdge("ed1", "v1", "v2", EdgeType.DIRECTED);
        g.addEdge("ed2", "v2", "v3", EdgeType.DIRECTED);

    }*/

    public SQFrame2() {
        this.setTitle("Example");
        this.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        this.setBackground(Color.white); // Establecer el color de fondo de la ventana

        initGraph();

        // Crear estructura de diseño circular del visor (nodo V, E y tipo de enlace)
        VisualizationViewer<Integer, String> vv =
                new VisualizationViewer<Integer, String>(new CircleLayout(g));

        // establecer etiqueta de texto de vértice
        vv.getRenderContext()
                .setVertexLabelTransformer(new ToStringLabeller());

        // establecer color de vértice
        vv.getRenderContext()
                .setVertexFillPaintTransformer((p) -> {
                    if (p == 2)
                        return Color.green;
                    else
                        return Color.YELLOW;
                });

        System.out.println(g.getEdges());
        String cr [] = {"#0000FF","#FF0000","#00FF00"};
        vv.getRenderContext().setVertexFillPaintTransformer((p) -> {
            if (p == 1){
                return Color.decode(cr[0]);
            }else if (p==2){
                return Color.decode(cr[1]);
            }else {
                return Color.decode(cr[2]);
            }
        });

        System.out.println("sucesor "+g.getSuccessors(1));
        System.out.println("lista de vertices "+g.getVertices());
        //int hh = (int) g.getSuccessors(1);

        // Establecer la etiqueta de texto del borde
        vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller());
        // Establecer el estilo de línea del borde
        vv.getRenderContext().setEdgeStrokeTransformer(p -> {
            return new BasicStroke(5f);
        });

        DefaultModalGraphMouse<Integer, String> gm = new DefaultModalGraphMouse<Integer, String>();
        gm.setMode(ModalGraphMouse.Mode.PICKING);
        vv.setGraphMouse(gm);
        // Coloque el objeto anterior en un contenedor Swing y muéstrelo
        getContentPane().add(vv);
        pack();

        //System.out.println(getAdyacents(g));

        DijkstraDistance dis = new DijkstraDistance(g);
        System.out.println(dis.getDistance(1,3));

        System.out.println(dis.getDistanceMap(1,3));


        DijkstraShortestPath dd = new DijkstraShortestPath(g);
        System.out.println(dd.getIncomingEdgeMap(1,3));
    }


    public List getAdyacents(SparseGraph vertex){
        //el .stream() convierte la lista en un stream
        //el .map transforma cada uno de los objetos que contiene la colección, en este caso a los getInfo de cada objeto
        //el .collect(Collectors.toList()) es para volver a convertir la lista a una estandar
        List adyacents = (List) vertex.getSuccessors(g).stream();
        return adyacents;
        //return null;
    }



    public static void main(String[] args) {
        SQFrame2 myframe = new SQFrame2();
        myframe.setExtendedState(JFrame.DISPOSE_ON_CLOSE);
        myframe.setDefaultCloseOperation(EXIT_ON_CLOSE);
        myframe.setVisible(true);
    }
}