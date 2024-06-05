package doctus.swing

import doctus.core.{LeafDoctusController, OwlDoctusController}

import java.awt.{BorderLayout, CardLayout, Dimension, FlowLayout}
import javax.swing.{ImageIcon, JButton, JFrame, JPanel, WindowConstants}

object ShowcaseCard extends App {

  // Activate openGL
  ShowcaseUtil.setupSwing()

  // Create owl components
  val owlPanel = new JPanel()
  owlPanel.setLayout(new BorderLayout())

  val owlNextButton = new JButton("next")
  val owlPreviousButton = new JButton("previous")
  val owlOwlButton = new JButton("owl")
  val owlLeafButton = new JButton("leaf")

  val owlTaskPanel = new JPanel()
  owlTaskPanel.setLayout(new FlowLayout())
  owlTaskPanel.add(owlNextButton)
  owlTaskPanel.add(owlPreviousButton)
  owlTaskPanel.add(owlOwlButton)
  owlTaskPanel.add(owlLeafButton)

  val owlSwingComponent: DoctusSwingComponent =
    DoctusSwingComponentFactory.component

  owlPanel.add(owlTaskPanel, BorderLayout.NORTH)
  owlPanel.add(owlSwingComponent, BorderLayout.CENTER)

  val owlDoctusSched = DoctusSchedulerSwing
  val owlDoctusCanvas = DoctusCanvasSwing(owlSwingComponent)
  val owlDoctusImage = DoctusImageSwing("logo.png")

  val owlDoctusNext = DoctusActivatableSwing(owlNextButton)
  val owlDoctusPrevious = DoctusActivatableSwing(owlPreviousButton)
  val owlDoctusOwl = DoctusActivatableSwing(owlOwlButton)
  val owlDoctusLeaf = DoctusActivatableSwing(owlLeafButton)

  // create leaf components

  val leafPanel = new JPanel()
  leafPanel.setLayout(new BorderLayout())

  val leafNextButton = new JButton("next")
  val leafPreviousButton = new JButton("previous")
  val leafLeafButton = new JButton("leaf")
  val leafOwlButton = new JButton("owl")

  val leafTaskPanel = new JPanel()
  leafTaskPanel.setLayout(new FlowLayout())
  leafTaskPanel.add(leafNextButton)
  leafTaskPanel.add(leafPreviousButton)
  leafTaskPanel.add(leafLeafButton)
  leafTaskPanel.add(leafOwlButton)

  val leafSwingComponent: DoctusSwingComponent =
    DoctusSwingComponentFactory.component

  leafPanel.add(leafTaskPanel, BorderLayout.NORTH)
  leafPanel.add(leafSwingComponent, BorderLayout.CENTER)

  val leafDoctusSched = DoctusSchedulerSwing
  val leafDoctusCanvas = DoctusCanvasSwing(leafSwingComponent)
  val leafDoctusImages = Seq(
    DoctusImageSwing("l1.png"),
    DoctusImageSwing("l2.png"),
    DoctusImageSwing("l3.png"),
    DoctusImageSwing("l4.png")
  )

  val leafDoctusNext = DoctusActivatableSwing(leafNextButton)
  val leafDoctusPrevious = DoctusActivatableSwing(leafPreviousButton)
  val leafDoctusOwl = DoctusActivatableSwing(leafOwlButton)
  val leafDoctusLeaf = DoctusActivatableSwing(leafLeafButton)

  // Put all together
  val cardPanel = new JPanel()
  private val cardLayout = new CardLayout()
  cardPanel.setLayout(cardLayout)
  cardPanel.add(owlPanel, "owl")
  cardPanel.add(leafPanel, "leaf")

  val doctusCard = DoctusSwingCard(cardPanel, cardLayout)

  // Start the controllers
  OwlDoctusController(
    owlDoctusCanvas,
    owlDoctusSched,
    owlDoctusImage,
    doctusCard,
    owlDoctusNext,
    owlDoctusPrevious,
    owlDoctusOwl,
    owlDoctusLeaf
  )
  LeafDoctusController(
    leafDoctusCanvas,
    leafDoctusSched,
    leafDoctusImages,
    doctusCard,
    leafDoctusNext,
    leafDoctusPrevious,
    leafDoctusOwl,
    leafDoctusLeaf
  )

  // Open the main frame
  private val top = new JFrame()
  top.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
  private val url = getClass.getClassLoader.getResource("logo.png")
  if (url != null) top.setIconImage(new ImageIcon(url).getImage)
  top.setTitle("Animated Showcase")
  top.getContentPane.add(cardPanel, BorderLayout.CENTER)
  top.setSize(new Dimension(2200, 1800))
  top.setVisible(true)

}
