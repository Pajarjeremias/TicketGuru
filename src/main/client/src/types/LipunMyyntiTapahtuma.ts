import { LipunMyyntiLipputyyppi } from "./LipunMyyntiLipputyyppi";

export type LipunMyyntiTapahtuma = {
  nimi: string;
  aika: Date;
  kuvaus: string;
  lipputyypit: LipunMyyntiLipputyyppi[];
}