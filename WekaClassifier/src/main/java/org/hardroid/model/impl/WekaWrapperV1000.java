/*
 * HARService: Activity Recognition Service
 * Copyright (C) 2015 agimenez
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *           http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.hardroid.model.impl;

class WekaWrapperV1000 {
    public static final int VERSION = 1000;

    public static double classify(Object[] i)
            throws Exception {

        double p = Double.NaN;
        p = WekaWrapperV1000.Nffc45590(i);
        return p;
    }
    static double Nffc45590(Object []i) {
        double p = Double.NaN;
        if (i[9] == null) {
            p = 0;
        } else if (((Double) i[9]).doubleValue() <= 1.8702293770815341) {
            p = WekaWrapperV1000.N83bd05b1(i);
        } else if (((Double) i[9]).doubleValue() > 1.8702293770815341) {
            p = WekaWrapperV1000.N141aba7217(i);
        }
        return p;
    }
    static double N83bd05b1(Object []i) {
        double p = Double.NaN;
        if (i[8] == null) {
            p = 3;
        } else if (((Double) i[8]).doubleValue() <= 0.06299034688155558) {
            p = WekaWrapperV1000.N7299d2672(i);
        } else if (((Double) i[8]).doubleValue() > 0.06299034688155558) {
            p = WekaWrapperV1000.N1565d58711(i);
        }
        return p;
    }
    static double N7299d2672(Object []i) {
        double p = Double.NaN;
        if (i[0] == null) {
            p = 3;
        } else if (((Double) i[0]).doubleValue() <= 9.872186174864428) {
            p = WekaWrapperV1000.N7eaff8cd3(i);
        } else if (((Double) i[0]).doubleValue() > 9.872186174864428) {
            p = WekaWrapperV1000.N451dbcd610(i);
        }
        return p;
    }
    static double N7eaff8cd3(Object []i) {
        double p = Double.NaN;
        if (i[10] == null) {
            p = 3;
        } else if (((Double) i[10]).doubleValue() <= -0.24662258067314508) {
            p = WekaWrapperV1000.N1394bccc4(i);
        } else if (((Double) i[10]).doubleValue() > -0.24662258067314508) {
            p = WekaWrapperV1000.N79cde7cf6(i);
        }
        return p;
    }
    static double N1394bccc4(Object []i) {
        double p = Double.NaN;
        if (i[3] == null) {
            p = 3;
        } else if (((Double) i[3]).doubleValue() <= 9.740502866483762) {
            p = WekaWrapperV1000.N457b0fed5(i);
        } else if (((Double) i[3]).doubleValue() > 9.740502866483762) {
            p = 3;
        }
        return p;
    }
    static double N457b0fed5(Object []i) {
        double p = Double.NaN;
        if (i[8] == null) {
            p = 3;
        } else if (((Double) i[8]).doubleValue() <= 0.05551745277382025) {
            p = 3;
        } else if (((Double) i[8]).doubleValue() > 0.05551745277382025) {
            p = 0;
        }
        return p;
    }
    static double N79cde7cf6(Object []i) {
        double p = Double.NaN;
        if (i[7] == null) {
            p = 0;
        } else if (((Double) i[7]).doubleValue() <= 6.90625) {
            p = 0;
        } else if (((Double) i[7]).doubleValue() > 6.90625) {
            p = WekaWrapperV1000.N782501ab7(i);
        }
        return p;
    }
    static double N782501ab7(Object []i) {
        double p = Double.NaN;
        if (i[10] == null) {
            p = 3;
        } else if (((Double) i[10]).doubleValue() <= -0.16427433053881618) {
            p = 3;
        } else if (((Double) i[10]).doubleValue() > -0.16427433053881618) {
            p = WekaWrapperV1000.N4ee1a0bf8(i);
        }
        return p;
    }
    static double N4ee1a0bf8(Object []i) {
        double p = Double.NaN;
        if (i[1] == null) {
            p = 3;
        } else if (((Double) i[1]).doubleValue() <= 0.033832439603715465) {
            p = WekaWrapperV1000.N5378dae99(i);
        } else if (((Double) i[1]).doubleValue() > 0.033832439603715465) {
            p = 0;
        }
        return p;
    }
    static double N5378dae99(Object []i) {
        double p = Double.NaN;
        if (i[11] == null) {
            p = 3;
        } else if (((Double) i[11]).doubleValue() <= -0.2782667166070238) {
            p = 3;
        } else if (((Double) i[11]).doubleValue() > -0.2782667166070238) {
            p = 0;
        }
        return p;
    }
    static double N451dbcd610(Object []i) {
        double p = Double.NaN;
        if (i[9] == null) {
            p = 0;
        } else if (((Double) i[9]).doubleValue() <= 1.2776890723704597) {
            p = 0;
        } else if (((Double) i[9]).doubleValue() > 1.2776890723704597) {
            p = 3;
        }
        return p;
    }
    static double N1565d58711(Object []i) {
        double p = Double.NaN;
        if (i[2] == null) {
            p = 0;
        } else if (((Double) i[2]).doubleValue() <= 13.20512616227174) {
            p = 0;
        } else if (((Double) i[2]).doubleValue() > 13.20512616227174) {
            p = WekaWrapperV1000.N56427bcc12(i);
        }
        return p;
    }
    static double N56427bcc12(Object []i) {
        double p = Double.NaN;
        if (i[2] == null) {
            p = 0;
        } else if (((Double) i[2]).doubleValue() <= 18.860251156045763) {
            p = WekaWrapperV1000.N43784c7f13(i);
        } else if (((Double) i[2]).doubleValue() > 18.860251156045763) {
            p = WekaWrapperV1000.N1500171916(i);
        }
        return p;
    }
    static double N43784c7f13(Object []i) {
        double p = Double.NaN;
        if (i[9] == null) {
            p = 0;
        } else if (((Double) i[9]).doubleValue() <= 1.6390522422501996) {
            p = 0;
        } else if (((Double) i[9]).doubleValue() > 1.6390522422501996) {
            p = WekaWrapperV1000.N6baba3ca14(i);
        }
        return p;
    }
    static double N6baba3ca14(Object []i) {
        double p = Double.NaN;
        if (i[13] == null) {
            p = 0;
        } else if (((Double) i[13]).doubleValue() <= 9.675918794501648) {
            p = 0;
        } else if (((Double) i[13]).doubleValue() > 9.675918794501648) {
            p = WekaWrapperV1000.N50d99ad515(i);
        }
        return p;
    }
    static double N50d99ad515(Object []i) {
        double p = Double.NaN;
        if (i[8] == null) {
            p = 1;
        } else if (((Double) i[8]).doubleValue() <= 1.5857560517780431) {
            p = 1;
        } else if (((Double) i[8]).doubleValue() > 1.5857560517780431) {
            p = 5;
        }
        return p;
    }
    static double N1500171916(Object []i) {
        double p = Double.NaN;
        if (i[0] == null) {
            p = 5;
        } else if (((Double) i[0]).doubleValue() <= 11.10686328481219) {
            p = 5;
        } else if (((Double) i[0]).doubleValue() > 11.10686328481219) {
            p = 1;
        }
        return p;
    }
    static double N141aba7217(Object []i) {
        double p = Double.NaN;
        if (i[2] == null) {
            p = 5;
        } else if (((Double) i[2]).doubleValue() <= 17.85974219492334) {
            p = WekaWrapperV1000.N2219fec818(i);
        } else if (((Double) i[2]).doubleValue() > 17.85974219492334) {
            p = WekaWrapperV1000.Nf265167101(i);
        }
        return p;
    }
    static double N2219fec818(Object []i) {
        double p = Double.NaN;
        if (i[8] == null) {
            p = 4;
        } else if (((Double) i[8]).doubleValue() <= 1.5046666121487373) {
            p = WekaWrapperV1000.N3e8ad76c19(i);
        } else if (((Double) i[8]).doubleValue() > 1.5046666121487373) {
            p = WekaWrapperV1000.N54d3d7e156(i);
        }
        return p;
    }
    static double N3e8ad76c19(Object []i) {
        double p = Double.NaN;
        if (i[3] == null) {
            p = 4;
        } else if (((Double) i[3]).doubleValue() <= 9.16304081994083) {
            p = WekaWrapperV1000.N2b27b77b20(i);
        } else if (((Double) i[3]).doubleValue() > 9.16304081994083) {
            p = 4;
        }
        return p;
    }
    static double N2b27b77b20(Object []i) {
        double p = Double.NaN;
        if (i[2] == null) {
            p = 4;
        } else if (((Double) i[2]).doubleValue() <= 14.79835595960351) {
            p = WekaWrapperV1000.N6a2f514721(i);
        } else if (((Double) i[2]).doubleValue() > 14.79835595960351) {
            p = WekaWrapperV1000.N24a8626555(i);
        }
        return p;
    }
    static double N6a2f514721(Object []i) {
        double p = Double.NaN;
        if (i[0] == null) {
            p = 5;
        } else if (((Double) i[0]).doubleValue() <= 9.949157623799753) {
            p = WekaWrapperV1000.N7ad1d7422(i);
        } else if (((Double) i[0]).doubleValue() > 9.949157623799753) {
            p = WekaWrapperV1000.N1123a07c45(i);
        }
        return p;
    }
    static double N7ad1d7422(Object []i) {
        double p = Double.NaN;
        if (i[4] == null) {
            p = 4;
        } else if (((Double) i[4]).doubleValue() <= -0.940759372385977) {
            p = WekaWrapperV1000.N6ff48b5a23(i);
        } else if (((Double) i[4]).doubleValue() > -0.940759372385977) {
            p = WekaWrapperV1000.N6e2405126(i);
        }
        return p;
    }
    static double N6ff48b5a23(Object []i) {
        double p = Double.NaN;
        if (i[8] == null) {
            p = 4;
        } else if (((Double) i[8]).doubleValue() <= 0.9682102095628817) {
            p = 4;
        } else if (((Double) i[8]).doubleValue() > 0.9682102095628817) {
            p = WekaWrapperV1000.N797e65b024(i);
        }
        return p;
    }
    static double N797e65b024(Object []i) {
        double p = Double.NaN;
        if (i[7] == null) {
            p = 5;
        } else if (((Double) i[7]).doubleValue() <= 6.953125) {
            p = 5;
        } else if (((Double) i[7]).doubleValue() > 6.953125) {
            p = WekaWrapperV1000.N694bb6b125(i);
        }
        return p;
    }
    static double N694bb6b125(Object []i) {
        double p = Double.NaN;
        if (i[12] == null) {
            p = 5;
        } else if (((Double) i[12]).doubleValue() <= 0.04871287475772786) {
            p = 5;
        } else if (((Double) i[12]).doubleValue() > 0.04871287475772786) {
            p = 4;
        }
        return p;
    }
    static double N6e2405126(Object []i) {
        double p = Double.NaN;
        if (i[11] == null) {
            p = 4;
        } else if (((Double) i[11]).doubleValue() <= -0.3855045511585117) {
            p = 4;
        } else if (((Double) i[11]).doubleValue() > -0.3855045511585117) {
            p = WekaWrapperV1000.N4d07a58c27(i);
        }
        return p;
    }
    static double N4d07a58c27(Object []i) {
        double p = Double.NaN;
        if (i[9] == null) {
            p = 0;
        } else if (((Double) i[9]).doubleValue() <= 1.9992308258341935) {
            p = WekaWrapperV1000.N475e0a7928(i);
        } else if (((Double) i[9]).doubleValue() > 1.9992308258341935) {
            p = WekaWrapperV1000.N7394d21d29(i);
        }
        return p;
    }
    static double N475e0a7928(Object []i) {
        double p = Double.NaN;
        if (i[0] == null) {
            p = 0;
        } else if (((Double) i[0]).doubleValue() <= 9.860754691928515) {
            p = 0;
        } else if (((Double) i[0]).doubleValue() > 9.860754691928515) {
            p = 5;
        }
        return p;
    }
    static double N7394d21d29(Object []i) {
        double p = Double.NaN;
        if (i[6] == null) {
            p = 4;
        } else if (((Double) i[6]).doubleValue() <= 96.31027773379337) {
            p = WekaWrapperV1000.N67e0e4f630(i);
        } else if (((Double) i[6]).doubleValue() > 96.31027773379337) {
            p = WekaWrapperV1000.N173b064331(i);
        }
        return p;
    }
    static double N67e0e4f630(Object []i) {
        double p = Double.NaN;
        if (i[8] == null) {
            p = 4;
        } else if (((Double) i[8]).doubleValue() <= 1.3293389682741577) {
            p = 4;
        } else if (((Double) i[8]).doubleValue() > 1.3293389682741577) {
            p = 5;
        }
        return p;
    }
    static double N173b064331(Object []i) {
        double p = Double.NaN;
        if (i[3] == null) {
            p = 5;
        } else if (((Double) i[3]).doubleValue() <= 8.781424553376388) {
            p = WekaWrapperV1000.N2001db1f32(i);
        } else if (((Double) i[3]).doubleValue() > 8.781424553376388) {
            p = WekaWrapperV1000.N797b0f1139(i);
        }
        return p;
    }
    static double N2001db1f32(Object []i) {
        double p = Double.NaN;
        if (i[6] == null) {
            p = 5;
        } else if (((Double) i[6]).doubleValue() <= 99.21637420026342) {
            p = WekaWrapperV1000.N7550f13833(i);
        } else if (((Double) i[6]).doubleValue() > 99.21637420026342) {
            p = WekaWrapperV1000.N2855942938(i);
        }
        return p;
    }
    static double N7550f13833(Object []i) {
        double p = Double.NaN;
        if (i[11] == null) {
            p = 5;
        } else if (((Double) i[11]).doubleValue() <= 0.0936629934835641) {
            p = WekaWrapperV1000.N15f77c3034(i);
        } else if (((Double) i[11]).doubleValue() > 0.0936629934835641) {
            p = 5;
        }
        return p;
    }
    static double N15f77c3034(Object []i) {
        double p = Double.NaN;
        if (i[9] == null) {
            p = 5;
        } else if (((Double) i[9]).doubleValue() <= 2.266017473984169) {
            p = WekaWrapperV1000.N5ab179d335(i);
        } else if (((Double) i[9]).doubleValue() > 2.266017473984169) {
            p = WekaWrapperV1000.N66d7e2837(i);
        }
        return p;
    }
    static double N5ab179d335(Object []i) {
        double p = Double.NaN;
        if (i[8] == null) {
            p = 4;
        } else if (((Double) i[8]).doubleValue() <= 0.8027804499249154) {
            p = WekaWrapperV1000.N74c698b936(i);
        } else if (((Double) i[8]).doubleValue() > 0.8027804499249154) {
            p = 5;
        }
        return p;
    }
    static double N74c698b936(Object []i) {
        double p = Double.NaN;
        if (i[1] == null) {
            p = 5;
        } else if (((Double) i[1]).doubleValue() <= 0.6657617814193661) {
            p = 5;
        } else if (((Double) i[1]).doubleValue() > 0.6657617814193661) {
            p = 4;
        }
        return p;
    }
    static double N66d7e2837(Object []i) {
        double p = Double.NaN;
        if (i[1] == null) {
            p = 4;
        } else if (((Double) i[1]).doubleValue() <= 0.9189950998586243) {
            p = 4;
        } else if (((Double) i[1]).doubleValue() > 0.9189950998586243) {
            p = 1;
        }
        return p;
    }
    static double N2855942938(Object []i) {
        double p = Double.NaN;
        if (i[6] == null) {
            p = 4;
        } else if (((Double) i[6]).doubleValue() <= 99.38641024581294) {
            p = 4;
        } else if (((Double) i[6]).doubleValue() > 99.38641024581294) {
            p = 5;
        }
        return p;
    }
    static double N797b0f1139(Object []i) {
        double p = Double.NaN;
        if (i[6] == null) {
            p = 5;
        } else if (((Double) i[6]).doubleValue() <= 97.43284214419523) {
            p = 5;
        } else if (((Double) i[6]).doubleValue() > 97.43284214419523) {
            p = WekaWrapperV1000.N393853d740(i);
        }
        return p;
    }
    static double N393853d740(Object []i) {
        double p = Double.NaN;
        if (i[4] == null) {
            p = 4;
        } else if (((Double) i[4]).doubleValue() <= 0.20224758461250286) {
            p = WekaWrapperV1000.N54c9f6e641(i);
        } else if (((Double) i[4]).doubleValue() > 0.20224758461250286) {
            p = WekaWrapperV1000.N711b4a0b43(i);
        }
        return p;
    }
    static double N54c9f6e641(Object []i) {
        double p = Double.NaN;
        if (i[10] == null) {
            p = 4;
        } else if (((Double) i[10]).doubleValue() <= -1.8004005292944203) {
            p = WekaWrapperV1000.N50b9d48a42(i);
        } else if (((Double) i[10]).doubleValue() > -1.8004005292944203) {
            p = 4;
        }
        return p;
    }
    static double N50b9d48a42(Object []i) {
        double p = Double.NaN;
        if (i[10] == null) {
            p = 4;
        } else if (((Double) i[10]).doubleValue() <= -1.916902005179586) {
            p = 4;
        } else if (((Double) i[10]).doubleValue() > -1.916902005179586) {
            p = 5;
        }
        return p;
    }
    static double N711b4a0b43(Object []i) {
        double p = Double.NaN;
        if (i[7] == null) {
            p = 4;
        } else if (((Double) i[7]).doubleValue() <= 6.96875) {
            p = WekaWrapperV1000.Na46091444(i);
        } else if (((Double) i[7]).doubleValue() > 6.96875) {
            p = 5;
        }
        return p;
    }
    static double Na46091444(Object []i) {
        double p = Double.NaN;
        if (i[0] == null) {
            p = 4;
        } else if (((Double) i[0]).doubleValue() <= 9.897600354402929) {
            p = 4;
        } else if (((Double) i[0]).doubleValue() > 9.897600354402929) {
            p = 5;
        }
        return p;
    }
    static double N1123a07c45(Object []i) {
        double p = Double.NaN;
        if (i[8] == null) {
            p = 4;
        } else if (((Double) i[8]).doubleValue() <= 0.7032204761030076) {
            p = 4;
        } else if (((Double) i[8]).doubleValue() > 0.7032204761030076) {
            p = WekaWrapperV1000.Nf202b5646(i);
        }
        return p;
    }
    static double Nf202b5646(Object []i) {
        double p = Double.NaN;
        if (i[0] == null) {
            p = 4;
        } else if (((Double) i[0]).doubleValue() <= 10.42444072840509) {
            p = WekaWrapperV1000.N24c063ab47(i);
        } else if (((Double) i[0]).doubleValue() > 10.42444072840509) {
            p = WekaWrapperV1000.N540a72e753(i);
        }
        return p;
    }
    static double N24c063ab47(Object []i) {
        double p = Double.NaN;
        if (i[7] == null) {
            p = 4;
        } else if (((Double) i[7]).doubleValue() <= 6.921875) {
            p = 4;
        } else if (((Double) i[7]).doubleValue() > 6.921875) {
            p = WekaWrapperV1000.N1e74c08e48(i);
        }
        return p;
    }
    static double N1e74c08e48(Object []i) {
        double p = Double.NaN;
        if (i[7] == null) {
            p = 4;
        } else if (((Double) i[7]).doubleValue() <= 6.9375) {
            p = WekaWrapperV1000.N12d8b37c49(i);
        } else if (((Double) i[7]).doubleValue() > 6.9375) {
            p = WekaWrapperV1000.N39e72bb52(i);
        }
        return p;
    }
    static double N12d8b37c49(Object []i) {
        double p = Double.NaN;
        if (i[4] == null) {
            p = 5;
        } else if (((Double) i[4]).doubleValue() <= -0.6028866895345456) {
            p = 5;
        } else if (((Double) i[4]).doubleValue() > -0.6028866895345456) {
            p = WekaWrapperV1000.N1c2c50ea50(i);
        }
        return p;
    }
    static double N1c2c50ea50(Object []i) {
        double p = Double.NaN;
        if (i[4] == null) {
            p = 4;
        } else if (((Double) i[4]).doubleValue() <= 0.20122675520673927) {
            p = 4;
        } else if (((Double) i[4]).doubleValue() > 0.20122675520673927) {
            p = WekaWrapperV1000.N3bd6a5ff51(i);
        }
        return p;
    }
    static double N3bd6a5ff51(Object []i) {
        double p = Double.NaN;
        if (i[11] == null) {
            p = 5;
        } else if (((Double) i[11]).doubleValue() <= 0.09160750874746852) {
            p = 5;
        } else if (((Double) i[11]).doubleValue() > 0.09160750874746852) {
            p = 4;
        }
        return p;
    }
    static double N39e72bb52(Object []i) {
        double p = Double.NaN;
        if (i[5] == null) {
            p = 5;
        } else if (((Double) i[5]).doubleValue() <= -0.9867437027014083) {
            p = 5;
        } else if (((Double) i[5]).doubleValue() > -0.9867437027014083) {
            p = 4;
        }
        return p;
    }
    static double N540a72e753(Object []i) {
        double p = Double.NaN;
        if (i[3] == null) {
            p = 4;
        } else if (((Double) i[3]).doubleValue() <= 8.106312989055509) {
            p = WekaWrapperV1000.N2a5fd38c54(i);
        } else if (((Double) i[3]).doubleValue() > 8.106312989055509) {
            p = 5;
        }
        return p;
    }
    static double N2a5fd38c54(Object []i) {
        double p = Double.NaN;
        if (i[13] == null) {
            p = 5;
        } else if (((Double) i[13]).doubleValue() <= 8.989010148687743) {
            p = 5;
        } else if (((Double) i[13]).doubleValue() > 8.989010148687743) {
            p = 4;
        }
        return p;
    }
    static double N24a8626555(Object []i) {
        double p = Double.NaN;
        if (i[2] == null) {
            p = 4;
        } else if (((Double) i[2]).doubleValue() <= 17.050841891280598) {
            p = 4;
        } else if (((Double) i[2]).doubleValue() > 17.050841891280598) {
            p = 2;
        }
        return p;
    }
    static double N54d3d7e156(Object []i) {
        double p = Double.NaN;
        if (i[10] == null) {
            p = 5;
        } else if (((Double) i[10]).doubleValue() <= -1.38979460404539) {
            p = WekaWrapperV1000.N53491ad657(i);
        } else if (((Double) i[10]).doubleValue() > -1.38979460404539) {
            p = WekaWrapperV1000.N6767eff91(i);
        }
        return p;
    }
    static double N53491ad657(Object []i) {
        double p = Double.NaN;
        if (i[0] == null) {
            p = 5;
        } else if (((Double) i[0]).doubleValue() <= 10.07799553900821) {
            p = WekaWrapperV1000.N7de708058(i);
        } else if (((Double) i[0]).doubleValue() > 10.07799553900821) {
            p = WekaWrapperV1000.N313ab74b69(i);
        }
        return p;
    }
    static double N7de708058(Object []i) {
        double p = Double.NaN;
        if (i[2] == null) {
            p = 5;
        } else if (((Double) i[2]).doubleValue() <= 11.77970925648061) {
            p = WekaWrapperV1000.N6082915459(i);
        } else if (((Double) i[2]).doubleValue() > 11.77970925648061) {
            p = WekaWrapperV1000.N2aa42e7a60(i);
        }
        return p;
    }
    static double N6082915459(Object []i) {
        double p = Double.NaN;
        if (i[4] == null) {
            p = 4;
        } else if (((Double) i[4]).doubleValue() <= -0.3955908219573785) {
            p = 4;
        } else if (((Double) i[4]).doubleValue() > -0.3955908219573785) {
            p = 5;
        }
        return p;
    }
    static double N2aa42e7a60(Object []i) {
        double p = Double.NaN;
        if (i[0] == null) {
            p = 5;
        } else if (((Double) i[0]).doubleValue() <= 9.942556380531517) {
            p = 5;
        } else if (((Double) i[0]).doubleValue() > 9.942556380531517) {
            p = WekaWrapperV1000.N31a01e5261(i);
        }
        return p;
    }
    static double N31a01e5261(Object []i) {
        double p = Double.NaN;
        if (i[4] == null) {
            p = 5;
        } else if (((Double) i[4]).doubleValue() <= 0.28721077351926344) {
            p = 5;
        } else if (((Double) i[4]).doubleValue() > 0.28721077351926344) {
            p = WekaWrapperV1000.N458e229662(i);
        }
        return p;
    }
    static double N458e229662(Object []i) {
        double p = Double.NaN;
        if (i[9] == null) {
            p = 5;
        } else if (((Double) i[9]).doubleValue() <= 2.41467118558188) {
            p = WekaWrapperV1000.N31f2034263(i);
        } else if (((Double) i[9]).doubleValue() > 2.41467118558188) {
            p = WekaWrapperV1000.N3a2e3cf864(i);
        }
        return p;
    }
    static double N31f2034263(Object []i) {
        double p = Double.NaN;
        if (i[5] == null) {
            p = 4;
        } else if (((Double) i[5]).doubleValue() <= -0.48322873418524237) {
            p = 4;
        } else if (((Double) i[5]).doubleValue() > -0.48322873418524237) {
            p = 5;
        }
        return p;
    }
    static double N3a2e3cf864(Object []i) {
        double p = Double.NaN;
        if (i[2] == null) {
            p = 4;
        } else if (((Double) i[2]).doubleValue() <= 15.782859299133422) {
            p = WekaWrapperV1000.Nbcdf76165(i);
        } else if (((Double) i[2]).doubleValue() > 15.782859299133422) {
            p = 5;
        }
        return p;
    }
    static double Nbcdf76165(Object []i) {
        double p = Double.NaN;
        if (i[3] == null) {
            p = 4;
        } else if (((Double) i[3]).doubleValue() <= 8.109725593590927) {
            p = WekaWrapperV1000.N3fe3fe1c66(i);
        } else if (((Double) i[3]).doubleValue() > 8.109725593590927) {
            p = 5;
        }
        return p;
    }
    static double N3fe3fe1c66(Object []i) {
        double p = Double.NaN;
        if (i[0] == null) {
            p = 4;
        } else if (((Double) i[0]).doubleValue() <= 10.043293258073449) {
            p = WekaWrapperV1000.N1e2a12b167(i);
        } else if (((Double) i[0]).doubleValue() > 10.043293258073449) {
            p = 1;
        }
        return p;
    }
    static double N1e2a12b167(Object []i) {
        double p = Double.NaN;
        if (i[0] == null) {
            p = 1;
        } else if (((Double) i[0]).doubleValue() <= 9.98298274910079) {
            p = WekaWrapperV1000.N5528347068(i);
        } else if (((Double) i[0]).doubleValue() > 9.98298274910079) {
            p = 4;
        }
        return p;
    }
    static double N5528347068(Object []i) {
        double p = Double.NaN;
        if (i[0] == null) {
            p = 4;
        } else if (((Double) i[0]).doubleValue() <= 9.958041424144788) {
            p = 4;
        } else if (((Double) i[0]).doubleValue() > 9.958041424144788) {
            p = 1;
        }
        return p;
    }
    static double N313ab74b69(Object []i) {
        double p = Double.NaN;
        if (i[2] == null) {
            p = 5;
        } else if (((Double) i[2]).doubleValue() <= 14.59431828215516) {
            p = WekaWrapperV1000.N1189bc6e70(i);
        } else if (((Double) i[2]).doubleValue() > 14.59431828215516) {
            p = WekaWrapperV1000.N1e66ab7576(i);
        }
        return p;
    }
    static double N1189bc6e70(Object []i) {
        double p = Double.NaN;
        if (i[6] == null) {
            p = 4;
        } else if (((Double) i[6]).doubleValue() <= 104.28557544229817) {
            p = 4;
        } else if (((Double) i[6]).doubleValue() > 104.28557544229817) {
            p = WekaWrapperV1000.N5ff88b7471(i);
        }
        return p;
    }
    static double N5ff88b7471(Object []i) {
        double p = Double.NaN;
        if (i[4] == null) {
            p = 5;
        } else if (((Double) i[4]).doubleValue() <= 0.3192111395530337) {
            p = WekaWrapperV1000.N4b67bf4f72(i);
        } else if (((Double) i[4]).doubleValue() > 0.3192111395530337) {
            p = WekaWrapperV1000.N73b56ac574(i);
        }
        return p;
    }
    static double N4b67bf4f72(Object []i) {
        double p = Double.NaN;
        if (i[7] == null) {
            p = 4;
        } else if (((Double) i[7]).doubleValue() <= 6.90625) {
            p = 4;
        } else if (((Double) i[7]).doubleValue() > 6.90625) {
            p = WekaWrapperV1000.N2f03d98773(i);
        }
        return p;
    }
    static double N2f03d98773(Object []i) {
        double p = Double.NaN;
        if (i[4] == null) {
            p = 4;
        } else if (((Double) i[4]).doubleValue() <= -1.112600017125931) {
            p = 4;
        } else if (((Double) i[4]).doubleValue() > -1.112600017125931) {
            p = 5;
        }
        return p;
    }
    static double N73b56ac574(Object []i) {
        double p = Double.NaN;
        if (i[8] == null) {
            p = 5;
        } else if (((Double) i[8]).doubleValue() <= 1.9099399198698848) {
            p = 5;
        } else if (((Double) i[8]).doubleValue() > 1.9099399198698848) {
            p = WekaWrapperV1000.N688decec75(i);
        }
        return p;
    }
    static double N688decec75(Object []i) {
        double p = Double.NaN;
        if (i[0] == null) {
            p = 1;
        } else if (((Double) i[0]).doubleValue() <= 10.275103065989821) {
            p = 1;
        } else if (((Double) i[0]).doubleValue() > 10.275103065989821) {
            p = 4;
        }
        return p;
    }
    static double N1e66ab7576(Object []i) {
        double p = Double.NaN;
        if (i[8] == null) {
            p = 1;
        } else if (((Double) i[8]).doubleValue() <= 4.252792263637099) {
            p = WekaWrapperV1000.N2f1896df77(i);
        } else if (((Double) i[8]).doubleValue() > 4.252792263637099) {
            p = WekaWrapperV1000.N47fc9c1990(i);
        }
        return p;
    }
    static double N2f1896df77(Object []i) {
        double p = Double.NaN;
        if (i[1] == null) {
            p = 5;
        } else if (((Double) i[1]).doubleValue() <= 2.1182825864241184) {
            p = WekaWrapperV1000.N772332bb78(i);
        } else if (((Double) i[1]).doubleValue() > 2.1182825864241184) {
            p = WekaWrapperV1000.N309da18a81(i);
        }
        return p;
    }
    static double N772332bb78(Object []i) {
        double p = Double.NaN;
        if (i[4] == null) {
            p = 5;
        } else if (((Double) i[4]).doubleValue() <= 0.40585717042001584) {
            p = 5;
        } else if (((Double) i[4]).doubleValue() > 0.40585717042001584) {
            p = WekaWrapperV1000.N704e4a3f79(i);
        }
        return p;
    }
    static double N704e4a3f79(Object []i) {
        double p = Double.NaN;
        if (i[6] == null) {
            p = 1;
        } else if (((Double) i[6]).doubleValue() <= 107.96699561485603) {
            p = WekaWrapperV1000.N5bd87a4080(i);
        } else if (((Double) i[6]).doubleValue() > 107.96699561485603) {
            p = 1;
        }
        return p;
    }
    static double N5bd87a4080(Object []i) {
        double p = Double.NaN;
        if (i[0] == null) {
            p = 1;
        } else if (((Double) i[0]).doubleValue() <= 10.231307956930616) {
            p = 1;
        } else if (((Double) i[0]).doubleValue() > 10.231307956930616) {
            p = 2;
        }
        return p;
    }
    static double N309da18a81(Object []i) {
        double p = Double.NaN;
        if (i[3] == null) {
            p = 5;
        } else if (((Double) i[3]).doubleValue() <= 4.407536544907301) {
            p = WekaWrapperV1000.N5ec5fbc882(i);
        } else if (((Double) i[3]).doubleValue() > 4.407536544907301) {
            p = WekaWrapperV1000.N44ca263c84(i);
        }
        return p;
    }
    static double N5ec5fbc882(Object []i) {
        double p = Double.NaN;
        if (i[3] == null) {
            p = 4;
        } else if (((Double) i[3]).doubleValue() <= 3.908820094996991) {
            p = WekaWrapperV1000.N5cd84d9d83(i);
        } else if (((Double) i[3]).doubleValue() > 3.908820094996991) {
            p = 5;
        }
        return p;
    }
    static double N5cd84d9d83(Object []i) {
        double p = Double.NaN;
        if (i[1] == null) {
            p = 4;
        } else if (((Double) i[1]).doubleValue() <= 2.7319257774619157) {
            p = 4;
        } else if (((Double) i[1]).doubleValue() > 2.7319257774619157) {
            p = 5;
        }
        return p;
    }
    static double N44ca263c84(Object []i) {
        double p = Double.NaN;
        if (i[13] == null) {
            p = 5;
        } else if (((Double) i[13]).doubleValue() <= 8.839060847909556) {
            p = WekaWrapperV1000.N19865ede85(i);
        } else if (((Double) i[13]).doubleValue() > 8.839060847909556) {
            p = WekaWrapperV1000.N686cfd9d86(i);
        }
        return p;
    }
    static double N19865ede85(Object []i) {
        double p = Double.NaN;
        if (i[1] == null) {
            p = 5;
        } else if (((Double) i[1]).doubleValue() <= 2.547949110849053) {
            p = 5;
        } else if (((Double) i[1]).doubleValue() > 2.547949110849053) {
            p = 1;
        }
        return p;
    }
    static double N686cfd9d86(Object []i) {
        double p = Double.NaN;
        if (i[0] == null) {
            p = 1;
        } else if (((Double) i[0]).doubleValue() <= 10.145863116013782) {
            p = WekaWrapperV1000.N76c6f1a887(i);
        } else if (((Double) i[0]).doubleValue() > 10.145863116013782) {
            p = WekaWrapperV1000.N63a8d28a88(i);
        }
        return p;
    }
    static double N76c6f1a887(Object []i) {
        double p = Double.NaN;
        if (i[7] == null) {
            p = 5;
        } else if (((Double) i[7]).doubleValue() <= 6.953125) {
            p = 5;
        } else if (((Double) i[7]).doubleValue() > 6.953125) {
            p = 1;
        }
        return p;
    }
    static double N63a8d28a88(Object []i) {
        double p = Double.NaN;
        if (i[1] == null) {
            p = 4;
        } else if (((Double) i[1]).doubleValue() <= 2.3617126567247064) {
            p = WekaWrapperV1000.N48f3008f89(i);
        } else if (((Double) i[1]).doubleValue() > 2.3617126567247064) {
            p = 1;
        }
        return p;
    }
    static double N48f3008f89(Object []i) {
        double p = Double.NaN;
        if (i[2] == null) {
            p = 4;
        } else if (((Double) i[2]).doubleValue() <= 16.389554723800263) {
            p = 4;
        } else if (((Double) i[2]).doubleValue() > 16.389554723800263) {
            p = 1;
        }
        return p;
    }
    static double N47fc9c1990(Object []i) {
        double p = Double.NaN;
        if (i[9] == null) {
            p = 1;
        } else if (((Double) i[9]).doubleValue() <= 2.365281903635394) {
            p = 1;
        } else if (((Double) i[9]).doubleValue() > 2.365281903635394) {
            p = 5;
        }
        return p;
    }
    static double N6767eff91(Object []i) {
        double p = Double.NaN;
        if (i[12] == null) {
            p = 0;
        } else if (((Double) i[12]).doubleValue() <= 0.1854094218859732) {
            p = WekaWrapperV1000.N236fc03992(i);
        } else if (((Double) i[12]).doubleValue() > 0.1854094218859732) {
            p = WekaWrapperV1000.N73484ba393(i);
        }
        return p;
    }
    static double N236fc03992(Object []i) {
        double p = Double.NaN;
        if (i[10] == null) {
            p = 5;
        } else if (((Double) i[10]).doubleValue() <= -1.1952128057755667) {
            p = 5;
        } else if (((Double) i[10]).doubleValue() > -1.1952128057755667) {
            p = 0;
        }
        return p;
    }
    static double N73484ba393(Object []i) {
        double p = Double.NaN;
        if (i[13] == null) {
            p = 1;
        } else if (((Double) i[13]).doubleValue() <= 8.733945295962078) {
            p = WekaWrapperV1000.N50771d8794(i);
        } else if (((Double) i[13]).doubleValue() > 8.733945295962078) {
            p = WekaWrapperV1000.N654300f095(i);
        }
        return p;
    }
    static double N50771d8794(Object []i) {
        double p = Double.NaN;
        if (i[9] == null) {
            p = 1;
        } else if (((Double) i[9]).doubleValue() <= 2.2413025816233323) {
            p = 1;
        } else if (((Double) i[9]).doubleValue() > 2.2413025816233323) {
            p = 5;
        }
        return p;
    }
    static double N654300f095(Object []i) {
        double p = Double.NaN;
        if (i[1] == null) {
            p = 4;
        } else if (((Double) i[1]).doubleValue() <= 2.756549525776559) {
            p = WekaWrapperV1000.N38706a6996(i);
        } else if (((Double) i[1]).doubleValue() > 2.756549525776559) {
            p = 1;
        }
        return p;
    }
    static double N38706a6996(Object []i) {
        double p = Double.NaN;
        if (i[11] == null) {
            p = 4;
        } else if (((Double) i[11]).doubleValue() <= -0.26721863367441806) {
            p = WekaWrapperV1000.N9d42b9797(i);
        } else if (((Double) i[11]).doubleValue() > -0.26721863367441806) {
            p = WekaWrapperV1000.N6127840f98(i);
        }
        return p;
    }
    static double N9d42b9797(Object []i) {
        double p = Double.NaN;
        if (i[12] == null) {
            p = 4;
        } else if (((Double) i[12]).doubleValue() <= 0.4560305309063756) {
            p = 4;
        } else if (((Double) i[12]).doubleValue() > 0.4560305309063756) {
            p = 5;
        }
        return p;
    }
    static double N6127840f98(Object []i) {
        double p = Double.NaN;
        if (i[6] == null) {
            p = 5;
        } else if (((Double) i[6]).doubleValue() <= 100.13580084419856) {
            p = 5;
        } else if (((Double) i[6]).doubleValue() > 100.13580084419856) {
            p = WekaWrapperV1000.N1c1e5c5b99(i);
        }
        return p;
    }
    static double N1c1e5c5b99(Object []i) {
        double p = Double.NaN;
        if (i[7] == null) {
            p = 1;
        } else if (((Double) i[7]).doubleValue() <= 6.9375) {
            p = 1;
        } else if (((Double) i[7]).doubleValue() > 6.9375) {
            p = WekaWrapperV1000.N27864e93100(i);
        }
        return p;
    }
    static double N27864e93100(Object []i) {
        double p = Double.NaN;
        if (i[10] == null) {
            p = 4;
        } else if (((Double) i[10]).doubleValue() <= -1.2729267784079186) {
            p = 4;
        } else if (((Double) i[10]).doubleValue() > -1.2729267784079186) {
            p = 1;
        }
        return p;
    }
    static double Nf265167101(Object []i) {
        double p = Double.NaN;
        if (i[8] == null) {
            p = 1;
        } else if (((Double) i[8]).doubleValue() <= 5.003881112651289) {
            p = WekaWrapperV1000.N52fcab70102(i);
        } else if (((Double) i[8]).doubleValue() > 5.003881112651289) {
            p = WekaWrapperV1000.N1afd6509123(i);
        }
        return p;
    }
    static double N52fcab70102(Object []i) {
        double p = Double.NaN;
        if (i[0] == null) {
            p = 1;
        } else if (((Double) i[0]).doubleValue() <= 11.509297929714823) {
            p = WekaWrapperV1000.N3c6ec9f0103(i);
        } else if (((Double) i[0]).doubleValue() > 11.509297929714823) {
            p = WekaWrapperV1000.N3479d4e9119(i);
        }
        return p;
    }
    static double N3c6ec9f0103(Object []i) {
        double p = Double.NaN;
        if (i[3] == null) {
            p = 5;
        } else if (((Double) i[3]).doubleValue() <= 2.682580294257568) {
            p = WekaWrapperV1000.N2989fa38104(i);
        } else if (((Double) i[3]).doubleValue() > 2.682580294257568) {
            p = WekaWrapperV1000.N14192b07105(i);
        }
        return p;
    }
    static double N2989fa38104(Object []i) {
        double p = Double.NaN;
        if (i[8] == null) {
            p = 1;
        } else if (((Double) i[8]).doubleValue() <= 2.998639544910029) {
            p = 1;
        } else if (((Double) i[8]).doubleValue() > 2.998639544910029) {
            p = 5;
        }
        return p;
    }
    static double N14192b07105(Object []i) {
        double p = Double.NaN;
        if (i[8] == null) {
            p = 1;
        } else if (((Double) i[8]).doubleValue() <= 2.3808584086328892) {
            p = WekaWrapperV1000.N2389b5df106(i);
        } else if (((Double) i[8]).doubleValue() > 2.3808584086328892) {
            p = WekaWrapperV1000.N435cc9fb111(i);
        }
        return p;
    }
    static double N2389b5df106(Object []i) {
        double p = Double.NaN;
        if (i[2] == null) {
            p = 4;
        } else if (((Double) i[2]).doubleValue() <= 19.912529751044868) {
            p = WekaWrapperV1000.N6962c917107(i);
        } else if (((Double) i[2]).doubleValue() > 19.912529751044868) {
            p = WekaWrapperV1000.N52c0e5d6109(i);
        }
        return p;
    }
    static double N6962c917107(Object []i) {
        double p = Double.NaN;
        if (i[7] == null) {
            p = 4;
        } else if (((Double) i[7]).doubleValue() <= 6.9375) {
            p = 4;
        } else if (((Double) i[7]).doubleValue() > 6.9375) {
            p = WekaWrapperV1000.Nfdca639108(i);
        }
        return p;
    }
    static double Nfdca639108(Object []i) {
        double p = Double.NaN;
        if (i[1] == null) {
            p = 1;
        } else if (((Double) i[1]).doubleValue() <= 2.7618270048734637) {
            p = 1;
        } else if (((Double) i[1]).doubleValue() > 2.7618270048734637) {
            p = 4;
        }
        return p;
    }
    static double N52c0e5d6109(Object []i) {
        double p = Double.NaN;
        if (i[8] == null) {
            p = 4;
        } else if (((Double) i[8]).doubleValue() <= 1.4982229769117286) {
            p = 4;
        } else if (((Double) i[8]).doubleValue() > 1.4982229769117286) {
            p = WekaWrapperV1000.N3c09951c110(i);
        }
        return p;
    }
    static double N3c09951c110(Object []i) {
        double p = Double.NaN;
        if (i[0] == null) {
            p = 5;
        } else if (((Double) i[0]).doubleValue() <= 10.068495794846335) {
            p = 5;
        } else if (((Double) i[0]).doubleValue() > 10.068495794846335) {
            p = 1;
        }
        return p;
    }
    static double N435cc9fb111(Object []i) {
        double p = Double.NaN;
        if (i[2] == null) {
            p = 1;
        } else if (((Double) i[2]).doubleValue() <= 21.65767513786922) {
            p = WekaWrapperV1000.N29db42c7112(i);
        } else if (((Double) i[2]).doubleValue() > 21.65767513786922) {
            p = 1;
        }
        return p;
    }
    static double N29db42c7112(Object []i) {
        double p = Double.NaN;
        if (i[13] == null) {
            p = 5;
        } else if (((Double) i[13]).doubleValue() <= 7.469757488067028) {
            p = 5;
        } else if (((Double) i[13]).doubleValue() > 7.469757488067028) {
            p = WekaWrapperV1000.N7ce0fc7f113(i);
        }
        return p;
    }
    static double N7ce0fc7f113(Object []i) {
        double p = Double.NaN;
        if (i[10] == null) {
            p = 1;
        } else if (((Double) i[10]).doubleValue() <= -1.906308367802768) {
            p = WekaWrapperV1000.Nc31378a114(i);
        } else if (((Double) i[10]).doubleValue() > -1.906308367802768) {
            p = WekaWrapperV1000.N298724e6115(i);
        }
        return p;
    }
    static double Nc31378a114(Object []i) {
        double p = Double.NaN;
        if (i[9] == null) {
            p = 5;
        } else if (((Double) i[9]).doubleValue() <= 2.3363452786489316) {
            p = 5;
        } else if (((Double) i[9]).doubleValue() > 2.3363452786489316) {
            p = 1;
        }
        return p;
    }
    static double N298724e6115(Object []i) {
        double p = Double.NaN;
        if (i[2] == null) {
            p = 1;
        } else if (((Double) i[2]).doubleValue() <= 19.912529751044868) {
            p = WekaWrapperV1000.N5ab9966b116(i);
        } else if (((Double) i[2]).doubleValue() > 19.912529751044868) {
            p = 1;
        }
        return p;
    }
    static double N5ab9966b116(Object []i) {
        double p = Double.NaN;
        if (i[1] == null) {
            p = 1;
        } else if (((Double) i[1]).doubleValue() <= 2.914146326608752) {
            p = 1;
        } else if (((Double) i[1]).doubleValue() > 2.914146326608752) {
            p = WekaWrapperV1000.N21418a47117(i);
        }
        return p;
    }
    static double N21418a47117(Object []i) {
        double p = Double.NaN;
        if (i[0] == null) {
            p = 1;
        } else if (((Double) i[0]).doubleValue() <= 10.840835765413175) {
            p = WekaWrapperV1000.N24c96482118(i);
        } else if (((Double) i[0]).doubleValue() > 10.840835765413175) {
            p = 2;
        }
        return p;
    }
    static double N24c96482118(Object []i) {
        double p = Double.NaN;
        if (i[10] == null) {
            p = 2;
        } else if (((Double) i[10]).doubleValue() <= -1.6713054768813522) {
            p = 2;
        } else if (((Double) i[10]).doubleValue() > -1.6713054768813522) {
            p = 1;
        }
        return p;
    }
    static double N3479d4e9119(Object []i) {
        double p = Double.NaN;
        if (i[5] == null) {
            p = 2;
        } else if (((Double) i[5]).doubleValue() <= 1.525004851653251) {
            p = WekaWrapperV1000.N226516c3120(i);
        } else if (((Double) i[5]).doubleValue() > 1.525004851653251) {
            p = WekaWrapperV1000.N60d92b67122(i);
        }
        return p;
    }
    static double N226516c3120(Object []i) {
        double p = Double.NaN;
        if (i[1] == null) {
            p = 1;
        } else if (((Double) i[1]).doubleValue() <= 3.1258580679208525) {
            p = 1;
        } else if (((Double) i[1]).doubleValue() > 3.1258580679208525) {
            p = WekaWrapperV1000.Nf5bb37a121(i);
        }
        return p;
    }
    static double Nf5bb37a121(Object []i) {
        double p = Double.NaN;
        if (i[8] == null) {
            p = 2;
        } else if (((Double) i[8]).doubleValue() <= 4.85519629006318) {
            p = 2;
        } else if (((Double) i[8]).doubleValue() > 4.85519629006318) {
            p = 5;
        }
        return p;
    }
    static double N60d92b67122(Object []i) {
        double p = Double.NaN;
        if (i[8] == null) {
            p = 5;
        } else if (((Double) i[8]).doubleValue() <= 3.036058897285937) {
            p = 5;
        } else if (((Double) i[8]).doubleValue() > 3.036058897285937) {
            p = 1;
        }
        return p;
    }
    static double N1afd6509123(Object []i) {
        double p = Double.NaN;
        if (i[4] == null) {
            p = 5;
        } else if (((Double) i[4]).doubleValue() <= 0.19566339382829082) {
            p = WekaWrapperV1000.N1024ab9b124(i);
        } else if (((Double) i[4]).doubleValue() > 0.19566339382829082) {
            p = WekaWrapperV1000.N2f747e4c128(i);
        }
        return p;
    }
    static double N1024ab9b124(Object []i) {
        double p = Double.NaN;
        if (i[0] == null) {
            p = 5;
        } else if (((Double) i[0]).doubleValue() <= 11.113095465520917) {
            p = WekaWrapperV1000.N42361a86125(i);
        } else if (((Double) i[0]).doubleValue() > 11.113095465520917) {
            p = WekaWrapperV1000.N6b1d83b4126(i);
        }
        return p;
    }
    static double N42361a86125(Object []i) {
        double p = Double.NaN;
        if (i[2] == null) {
            p = 5;
        } else if (((Double) i[2]).doubleValue() <= 21.74643850114458) {
            p = 5;
        } else if (((Double) i[2]).doubleValue() > 21.74643850114458) {
            p = 1;
        }
        return p;
    }
    static double N6b1d83b4126(Object []i) {
        double p = Double.NaN;
        if (i[9] == null) {
            p = 2;
        } else if (((Double) i[9]).doubleValue() <= 2.3772114640439574) {
            p = 2;
        } else if (((Double) i[9]).doubleValue() > 2.3772114640439574) {
            p = WekaWrapperV1000.N1ab966f3127(i);
        }
        return p;
    }
    static double N1ab966f3127(Object []i) {
        double p = Double.NaN;
        if (i[3] == null) {
            p = 5;
        } else if (((Double) i[3]).doubleValue() <= 1.8755094503691585) {
            p = 5;
        } else if (((Double) i[3]).doubleValue() > 1.8755094503691585) {
            p = 2;
        }
        return p;
    }
    static double N2f747e4c128(Object []i) {
        double p = Double.NaN;
        if (i[1] == null) {
            p = 2;
        } else if (((Double) i[1]).doubleValue() <= 4.783737003779161) {
            p = WekaWrapperV1000.N5d64b30e129(i);
        } else if (((Double) i[1]).doubleValue() > 4.783737003779161) {
            p = WekaWrapperV1000.N7fb2566a133(i);
        }
        return p;
    }
    static double N5d64b30e129(Object []i) {
        double p = Double.NaN;
        if (i[5] == null) {
            p = 2;
        } else if (((Double) i[5]).doubleValue() <= 0.10407768080703717) {
            p = WekaWrapperV1000.N5a638b35130(i);
        } else if (((Double) i[5]).doubleValue() > 0.10407768080703717) {
            p = WekaWrapperV1000.Nbbce074131(i);
        }
        return p;
    }
    static double N5a638b35130(Object []i) {
        double p = Double.NaN;
        if (i[2] == null) {
            p = 5;
        } else if (((Double) i[2]).doubleValue() <= 19.015689360304975) {
            p = 5;
        } else if (((Double) i[2]).doubleValue() > 19.015689360304975) {
            p = 2;
        }
        return p;
    }
    static double Nbbce074131(Object []i) {
        double p = Double.NaN;
        if (i[0] == null) {
            p = 1;
        } else if (((Double) i[0]).doubleValue() <= 11.745677785045784) {
            p = WekaWrapperV1000.N7b66951132(i);
        } else if (((Double) i[0]).doubleValue() > 11.745677785045784) {
            p = 2;
        }
        return p;
    }
    static double N7b66951132(Object []i) {
        double p = Double.NaN;
        if (i[12] == null) {
            p = 1;
        } else if (((Double) i[12]).doubleValue() <= 0.1760133202980855) {
            p = 1;
        } else if (((Double) i[12]).doubleValue() > 0.1760133202980855) {
            p = 2;
        }
        return p;
    }
    static double N7fb2566a133(Object []i) {
        double p = Double.NaN;
        if (i[5] == null) {
            p = 2;
        } else if (((Double) i[5]).doubleValue() <= 2.1933163755639287) {
            p = 2;
        } else if (((Double) i[5]).doubleValue() > 2.1933163755639287) {
            p = WekaWrapperV1000.N394e508a134(i);
        }
        return p;
    }
    static double N394e508a134(Object []i) {
        double p = Double.NaN;
        if (i[7] == null) {
            p = 2;
        } else if (((Double) i[7]).doubleValue() <= 6.96875) {
            p = WekaWrapperV1000.N559da897135(i);
        } else if (((Double) i[7]).doubleValue() > 6.96875) {
            p = 1;
        }
        return p;
    }
    static double N559da897135(Object []i) {
        double p = Double.NaN;
        if (i[5] == null) {
            p = 2;
        } else if (((Double) i[5]).doubleValue() <= 3.156827974276167) {
            p = 2;
        } else if (((Double) i[5]).doubleValue() > 3.156827974276167) {
            p = 1;
        }
        return p;
    }
}
