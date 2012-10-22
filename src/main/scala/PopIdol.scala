package com.svgobjects

import unfiltered.request._
import unfiltered.response._

import org.clapper.avsl.Logger

/** unfiltered plan */
class App extends unfiltered.filter.Plan {

  val logger = Logger(classOf[App])

  /*
   * votes count
   * Both idols start with equal weighting
   */
  var gvotes:Double = 1
  var wvotes:Double = 1
  def total = gvotes + wvotes

  def intent = {
    case GET(Path("/")) =>
      Ok ~> Html(page)
    case GET(Path("/vote/will")) => 
      Ok ~> {
        wvotes+=1
        Html(page)
      }
    case GET(Path("/vote/garath")) => 
      Ok ~> {
        gvotes+=1
        Html(page)
      }
  }
  
  def page = 
    <html>
	  <head>
	  	<title>PopIdol</title>
	  	<link href="css/popidol.css" rel="stylesheet" type="text/css" />
	  </head>
      <body>
        <img src="/assets/img/popidol.gif" />
	  	<h1>Click on your favourite Pop Idol to vote for them</h1>

	  	<svg xmlns="http://www.w3.org/2000/svg" version="1.1">
	<g id="Layer_1_2_" transform="scale(1.6)">
            <a xlink:href="/vote/garath">
                <g transform={ "scale(" + gvotes/total + ")" }>
			<defs>
				<path id="XMLID_2_" d="M312.304,359.029l-87.311-71.651l-105.388,39.76l37.548-114.943l-64.389-96.462l110.512,0.616
					l65.594-99.376l30.755,115.324l104.923,35.042l-91.503,70.659L312.304,359.029z"/>
			</defs>
			<clipPath id="XMLID_4_">
				<use xlink:href="#XMLID_2_" />
			</clipPath>
			<g>
				<g>
					<defs>
						<path id="XMLID_6_" d="M21.8,356.418L31.425,0l382.499,10.361L404.3,366.78L21.8,356.418z"/>
					</defs>
					<clipPath id="XMLID_8_" clip-path="url(#XMLID_4_)">
						<use xlink:href="#XMLID_6_" />
					</clipPath>
					<g transform="matrix(1 1.513335e-09 2.298206e-09 1 -3.814697e-06 -6.911989e-08)" clip-path="url(#XMLID_8_)">
						<image xlink:href="/assets/img/gareth.jpg" 	height="400" id="XMLID_10_" transform="matrix(0.8279 0.0224 -0.0241 0.891 31.4253 0)" width="462" />
					</g>
				</g>
			</g>
                </g>
            </a>
            <a xlink:href="/vote/will">
                <g transform={ "scale(" + wvotes/total + ")" }>
			<path d="M623.438,440.373l-114.242,25.283l-44.711,108.125l-58.453-100.469l-116.446-8.168l78.114-87.377l-27.252-113.173
				l106.729,46.468l99.604-61.776l-12.152,116.095L623.438,440.373z"/>
			<g>
				<defs>
					<path id="XMLID_9_" d="M623.495,440.451l-114.242,25.285L464.54,573.859l-58.454-100.469l-116.444-8.168l78.114-87.375
						l-27.254-113.174l106.731,46.468l99.602-61.777l-12.152,116.096L623.495,440.451z"/>
				</defs>
				<clipPath id="XMLID_12_">
					<use xlink:href="#XMLID_9_" />
				</clipPath>
				<g>
					<g>
						<defs>
							<path id="XMLID_11_" d="M0,517.021l16.048-330.636l533.57,25.897l-16.049,330.635L0,517.021z"/>
						</defs>
						<clipPath id="XMLID_13_" clip-path="url(#XMLID_12_)">
							<use xlink:href="#XMLID_11_" />
						</clipPath>
						<g transform="matrix(1 -2.799101e-09 -3.035266e-09 1 1.907349e-06 0)" clip-path="url(#XMLID_13_)">	
							<image xlink:href="/assets/img/will.jpg" height="346" id="XMLID_14_" transform="matrix(0.9881 0.048 -0.0464 0.9556 16.0479 186.3857)" width="540" />
						</g>
					</g>
				</g>
			</g>
                </g>
            </a>
	</g>
	  	</svg>
      </body>
    </html>
}

/** embedded server */
object Server {
  def main(args: Array[String]) {
    val http = unfiltered.jetty.Http.local(8080)
    http.context("/assets") { _.resources(new java.net.URL(getClass().getResource("/www/img"), ".")) }
      .filter(new App).run()
  }
}
