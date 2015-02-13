/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package smartfriend.handGesture;

/**
 *
 * @author Meuru
 */
public enum FingerName {

    LITTLE, RING, MIDDLE, INDEX, THUMB,
    UNKNOWN;

    public FingerName getNext() {
        int nextIdx = ordinal() + 1;
        if (nextIdx == (values().length)) {
            nextIdx = 0;
        }
        return values()[nextIdx];
    }

    public FingerName getPrev() {
        int prevIdx = ordinal() - 1;
        if (prevIdx < 0) {
            prevIdx = values().length - 1;
        }
        return values()[prevIdx];
    }
}
