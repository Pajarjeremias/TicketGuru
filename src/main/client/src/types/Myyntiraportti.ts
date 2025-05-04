/* eslint-disable @typescript-eslint/no-explicit-any */

export type Myyntiraportti = {
  id: number;
  aika: Date | string;
  summa: number;
  liput: any[]
}

export const tyhjaMyyntiRportti: Myyntiraportti = {
  id: -1,
  aika: new Date(),
  summa: 0,
  liput: []
}