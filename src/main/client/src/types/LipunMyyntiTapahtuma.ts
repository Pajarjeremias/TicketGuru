import { LipunMyyntiLipputyyppi } from "./LipunMyyntiLipputyyppi";

export type LipunMyyntiTapahtuma = {
  id: number;
  maxLiput: number;
  currentLiput: number;
  nimi: string;
  aika: Date;
  kuvaus: string;
  lipputyypit: LipunMyyntiLipputyyppi[];
}