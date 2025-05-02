import { LipunMyyntiTapahtuma } from "./LipunMyyntiTapahtuma";
import { Myyntiraportti } from "./Myyntiraportti"

export type PDFprops = {
  myyntiYhteenveto: Myyntiraportti;
  myyLippuLista: LipunMyyntiTapahtuma[];
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  qrDataUrls: any[];
}