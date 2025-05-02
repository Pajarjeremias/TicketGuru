/* eslint-disable @typescript-eslint/no-explicit-any */
import { Document, Page, Text, Image, StyleSheet } from '@react-pdf/renderer';
import { PDFprops } from '../types/PDFprops';

// Create styles
const styles = StyleSheet.create({
  body: {
    paddingTop: 35,
    paddingBottom: 65,
    paddingHorizontal: 35,
  },
  title: {
    fontSize: 24,
    textAlign: 'center',
  },
  author: {
    fontSize: 12,
    textAlign: 'center',
    marginBottom: 40,
  },
  subtitle: {
    fontSize: 18,
    margin: 12,
  },
  text: {
    margin: 12,
    fontSize: 14,
    textAlign: 'justify'
  },
  image: {
    marginVertical: 15,
    marginHorizontal: 100,
  },
  header: {
    fontSize: 12,
    marginBottom: 20,
    textAlign: 'center',
    color: 'grey',
  },
  pageNumber: {
    position: 'absolute',
    fontSize: 12,
    bottom: 30,
    left: 0,
    right: 0,
    textAlign: 'center',
    color: 'grey',
  },
});

// Create Document Component
export default function LiputPDFComponent( props: PDFprops ) {
  const getTapahtumaNimi = (lippu: { tapahtuman_lipputyyppi: { tapahtuma_lipputyyppi_id: number; }; }) => {
    let nimi = "Ei löytynyt";
    props.myyLippuLista.forEach((t:any) => {
      t.lipputyypit.forEach((l:any) => {
        if (lippu.tapahtuman_lipputyyppi.tapahtuma_lipputyyppi_id === l.id) {
          nimi = t.nimi;
        }
      })
    })
    return nimi;
  }

  return(
    <Document>
      {props.myyntiYhteenveto.liput.map((l, index) =>
        <Page size="A4" style={styles.body} key={index}>
          <Text style={styles.header}>
            {index + 1} / {props.myyntiYhteenveto.liput.length}
          </Text>
          <Text style={styles.title}>
            Lippu {index + 1}
          </Text>
          <Text style={styles.text}>
            Tapahtuma: {getTapahtumaNimi(l)}
          </Text>
          <Text style={styles.text}>
            Lipputyyppi: {(l.tapahtuman_lipputyyppi.lipputyyppi.lipputyyppi)}
          </Text>
          <Text style={styles.text}>
            Hinta: €{(l.hinta.toFixed(2))}
          </Text>

          <Text style={styles.text}>
            Koodi: {(l.koodi)}
          </Text>

          <Image
            key={index}
            src={props.qrDataUrls[index]}
            style={{ width: 200, height: 200, marginLeft: "auto", marginRight: "auto" }}
          />
        </Page>
      )}
    </Document>
  )
};
