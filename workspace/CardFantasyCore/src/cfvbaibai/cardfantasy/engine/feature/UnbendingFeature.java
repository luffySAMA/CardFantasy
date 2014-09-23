package cfvbaibai.cardfantasy.engine.feature;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Feature;
import cfvbaibai.cardfantasy.data.FeatureTag;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.CardStatusItem;
import cfvbaibai.cardfantasy.engine.CardStatusType;
import cfvbaibai.cardfantasy.engine.EntityInfo;
import cfvbaibai.cardfantasy.engine.FeatureEffect;
import cfvbaibai.cardfantasy.engine.FeatureEffectType;
import cfvbaibai.cardfantasy.engine.FeatureInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;

public class UnbendingFeature {

	public static void apply(FeatureResolver resolver, CardInfo card, FeatureInfo featureInfo) {
		Feature feature = featureInfo.getFeature();
		GameUI ui = resolver.getStage().getUI();
		ui.useSkill(card, feature, true);
		CardStatusItem status = CardStatusItem.unbended(featureInfo);
		ui.addCardStatus(card, card, feature, status);
		card.addStatus(status);
        ui.adjustHP(card, card, 1, feature);
        card.addEffect(new FeatureEffect(FeatureEffectType.MAXHP_CHANGE, featureInfo, 1, false));
        card.setUsed(featureInfo);
	}

	public static boolean isStatusEscaped(Feature cardFeature, FeatureResolver resolver, CardStatusItem item,
            CardInfo defender) {
        if (defender.getStatus().containsStatus(CardStatusType.不屈)) {
        	if (item.getType() == CardStatusType.冰冻 || item.getType() == CardStatusType.麻痹) {
                EntityInfo attacker = item.getCause().getOwner();
                GameUI ui = resolver.getStage().getUI();
                ui.useSkill(defender, attacker, cardFeature, true);
                ui.blockStatus(attacker, defender, cardFeature, item);
                return true;
            } else {
                return false;
            }
    	}
		return false;
	}

	public static boolean isFeatureEscaped(FeatureResolver resolver, Feature cardFeature, Feature attackFeature,
            EntityInfo attacker, CardInfo defender) {
        if (defender.getStatus().containsStatus(CardStatusType.不屈)) {
	        if (attackFeature.getType().containsTag(FeatureTag.控制)) {
	            GameUI ui = resolver.getStage().getUI();
	            ui.useSkill(defender, attacker, cardFeature, true);
	            ui.blockFeature(attacker, defender, cardFeature, attackFeature);
	            return true;
	        } else {
	            return false;
	        }
        }
		return false;
    }

}
